package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import com.softgallery.issuemanagementbackEnd.config.ChatGptConfig;
import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.ChatGptRequestDTO;
import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.ChatGptResponseDTO;
import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.QuestionRequestDTO;
import com.softgallery.issuemanagementbackEnd.dto.issue.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.service.issue.IssueServiceIF;
import com.softgallery.issuemanagementbackEnd.service.projectMember.ProjectMemberServiceIF;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatGptService implements ChatGptServiceIF {
    private final IssueServiceIF issueService;
    private final ProjectMemberServiceIF projectMemberService;
    private static RestTemplate restTemplate = new RestTemplate();
    private final String apiKey; // OpenAI API 키

    public ChatGptService(IssueServiceIF issueService, ProjectMemberServiceIF projectMemberService, @Value("${api-key}") String apiKey) {
        this.issueService = issueService;
        this.projectMemberService = projectMemberService;
        this.apiKey = apiKey;
    }

    public HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    @Override
    public Map<String, String> getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDTO.class);

        String output = responseEntity.getBody().getChoices().get(0).getMessage().getContent();

        JSONObject jsonObject = new JSONObject(output);
        Map<String, String> response = new HashMap<>();
        response.put("answer", jsonObject.getString("answer"));
        response.put("reason", jsonObject.getString("reason"));
        return response;
    }

    @Override
    public Map<String, String> selectUser(Long issueId) {
        IssueDTO issueDTO = issueService.getIssue(issueId);

        if(issueDTO==null) throw new RuntimeException("can not find Issue because issue id " + issueId + " is null");

        List<UserDTO> devsInProject= projectMemberService.getSpecificUsersOfRoleInProject(issueDTO.getProjectId(), Role.ROLE_DEVELOPER);

        List<String> devIdsInProject=new ArrayList<>();
        for(UserDTO userDTO:devsInProject) {
            devIdsInProject.add(userDTO.getId());
        }

        List<IssueDTO> relatedIssues=issueService.findAllIssuesRelatedAssignee(devIdsInProject);

        String content=makeQuestionStr(relatedIssues, issueDTO, devIdsInProject);

        QuestionRequestDTO requestDTO = new  QuestionRequestDTO(content);

        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDTO(
                                ChatGptConfig.MODEL,
                                requestDTO.getMessages(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
    }

    public String makeQuestionStr(List<IssueDTO> issues, IssueDTO currIssue, List<String> devIds) {
        String content = "previous datas: ";

        Iterator<IssueDTO> iterator = issues.iterator();
        while (iterator.hasNext()) {
            IssueDTO issue = iterator.next();
            content+="{\"assigneeId\"="+issue.getAssignee().getId()+", \"title\"="+issue.getTitle()+", \"description\"="+issue.getDescription()+", \"priority\"="+issue.getPriority()+", \"status\"="+issue.getStatus()+"}";
            if (iterator.hasNext()) {
                content+=", ";
            }
            else {
                content+="\n\n";
            }
        }

        content += ("currentIssue: { \"title\"=" + currIssue.getTitle()+", \"description\"="+currIssue.getDescription()+", \"priority\"="+currIssue.getPriority().name()+"}\n\n");

        content += ("candidates: {");

        Iterator<String> iter = devIds.iterator();
        while(iter.hasNext()) {
            String id = iter.next();
            content+=id;
            if(iter.hasNext()) {
                content+=", ";
            }
            else {
                content+="}";
            }
        }

        return content;
    }
}
