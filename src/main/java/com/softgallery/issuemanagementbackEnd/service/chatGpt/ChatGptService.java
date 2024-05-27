package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import com.softgallery.issuemanagementbackEnd.config.ChatGptConfig;
import com.softgallery.issuemanagementbackEnd.dto.*;
import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;
import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.IssueRepository;
import com.softgallery.issuemanagementbackEnd.repository.ProjectRepository;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.service.issue.IssueServiceIF;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import com.softgallery.issuemanagementbackEnd.service.projectMember.ProjectMemberServiceIF;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
public class ChatGptService implements ChatGptServiceIF {
    private final IssueServiceIF issueService;
    private final ProjectMemberServiceIF projectMememberService;
    private static RestTemplate restTemplate = new RestTemplate();
    private final String apiKey; // OpenAI API 키

    public ChatGptService(IssueServiceIF issueService, ProjectMemberServiceIF projectMememberService, @Value("${api-key}") String apiKey) {
        this.issueService = issueService;
        this.projectMememberService = projectMememberService;
        this.apiKey = apiKey;
    }

    public HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDTO.class);

        return responseEntity.getBody();
    }

    @Override
    public ChatGptResponseDTO selectUser(Long issueId) {
        IssueDTO issueDTO = issueService.getIssue(issueId);
        List<UserDTO> devsInProject= projectMememberService.getSpecificUsersOfRoleInProject(issueDTO.getProjectId(), Role.ROLE_DEVELOPER);

        List<String> devIdsInProject=new ArrayList<>();
        for(UserDTO userDTO:devsInProject) {
            devIdsInProject.add(userDTO.getId());
        }

        List<IssueDTO> relatedIssues=issueService.findAllIssuesRelatedAssignee(devIdsInProject);

        String content=makeQuestionStr(relatedIssues, issueDTO, devIdsInProject);
        System.out.println(content);

        QuestionRequestDTO requestDTO = new QuestionRequestDTO(content);

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
