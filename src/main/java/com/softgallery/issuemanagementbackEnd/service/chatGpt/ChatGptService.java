package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import com.softgallery.issuemanagementbackEnd.config.ChatGptConfig;
import com.softgallery.issuemanagementbackEnd.dto.ChatGptRequestDTO;
import com.softgallery.issuemanagementbackEnd.dto.ChatGptResponseDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.QuestionRequestDTO;
import com.softgallery.issuemanagementbackEnd.entity.IssueEntity;
import com.softgallery.issuemanagementbackEnd.entity.ProjectEntity;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.IssueRepository;
import com.softgallery.issuemanagementbackEnd.repository.ProjectRepository;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
public class ChatGptService implements ChatGptServiceIF {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private static RestTemplate restTemplate = new RestTemplate();
    private final String apiKey; // OpenAI API 키

    public ChatGptService(IssueRepository issueRepository, ProjectRepository projectRepository, @Value("${api-key}") String apiKey) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
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
        Optional<IssueEntity> issueEntity = issueRepository.findById(issueId);

        if(!issueEntity.isPresent()) {
            throw new RuntimeException("no such issue id " + issueId);
        }
        else {
            IssueEntity currIssueEntity = issueEntity.get();

            List<UserEntity> devs= userRepo

            List<IssueEntity> issues=issueRepository.findAllByProjectIdAndStatusNot(currIssueEntity.getProjectId(), State.NEW);

            String content=makeQuestionStr(issues);
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
    }

    public String makeQuestionStr(List<IssueEntity> issues, IssueEntity currIssue) {
        String content = "previous datas: ";

        Iterator<IssueEntity> iterator = issues.iterator();
        while (iterator.hasNext()) {
            IssueEntity issue = iterator.next();
            content+="{\"assigneeId\"="+issue.getAssigneeId()+", \"title\"="+issue.getTitle()+", \"description\"="+issue.getDescription()+", \"priority\"="+issue.getPriority()+", \"status\"="+issue.getStatus()+"}";
            if (iterator.hasNext()) {
                content+=", ";
            }
            else {
                content+="\n\n";
            }
        }

        content += ("currentIssue: { \"title\"=" + currIssue.getTitle()+", \"description\"="+currIssue.getDescription()+", \"priority\"="+currIssue.getPriority().name()+"}\n\n");

        return content;
    }
//
//    @Override
//    public ChatGptResponseDTO getTag(Long issueId) {
//        Optional<IssueEntity> issueEntity = issueRepository.findById(issueId);
//
//        if(!issueEntity.isPresent()) {
//            throw new RuntimeException("no such issue id " + issueId);
//        }
//        else {
//            IssueEntity currIssueEntity = issueEntity.get();
//            String content = currIssueEntity.getDescription();
//
//
//            QuestionRequestDTO requestDTO = new QuestionRequestDTO(content);
//
//            return this.getResponse(
//                    this.buildHttpEntity(
//                            new ChatGptRequestDTO(
//                                    ChatGptConfig.MODEL,
//                                    requestDTO.getMessages(),
//                                    ChatGptConfig.MAX_TOKEN,
//                                    ChatGptConfig.TEMPERATURE,
//                                    ChatGptConfig.TOP_P
//                            )
//                    )
//            );
//            HttpEntity<ChatGptRequestDTO> requestEntity = new HttpEntity<>(requestDTO);
//
//            ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
//                    ChatGptConfig.URL,
//                    chatGptRequestDtoHttpEntity,
//                    ChatGptResponseDTO.class);
//
//            ChatGptResponseDTO chatGptResponseDTO = new ChatGptResponseDTO()
//        }
//    }
}
