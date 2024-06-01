package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import com.softgallery.issuemanagementbackEnd.config.ChatGptConfig;
import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.*;
import com.softgallery.issuemanagementbackEnd.dto.issue.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import com.softgallery.issuemanagementbackEnd.service.issue.IssueServiceIF;
import com.softgallery.issuemanagementbackEnd.service.projectMember.ProjectMemberServiceIF;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatGptServiceTest {

    @Mock
    private IssueServiceIF issueService;

    @InjectMocks
    private ChatGptService chatGptService;

    private IssueDTO issueDTO;
    private UserDTO userDTO;
    private List<IssueDTO> relatedIssues;
    private List<UserDTO> devsInProject;
    private ChatGptResponseDTO chatGptResponseDTO;

    @BeforeEach
    void setUp() {
        // Test data setup
        userDTO = new UserDTO("dev1", "developer", "testDev@dev.com", "garbage", Role.ROLE_DEVELOPER);

        issueDTO = new IssueDTO(
                1L,
                "Test Issue",
                "This is a test issue",
                null,  // Assignee 설정
                State.NEW,
                Priority.MAJOR,
                userDTO,
                new ArrayList<>(),
                null,
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        );

        relatedIssues = new ArrayList<>();
        relatedIssues.add(issueDTO);

        devsInProject = new ArrayList<>();
        devsInProject.add(userDTO);

        List<Choice> choices = new ArrayList<>();
        Message message = new Message("{\n\"answer\": \"DEV00\",\n\"reason\": \"현재 이슈는 UI 문제로 중요한 문제이며, DEV00은 이전에 중요한 스프링 권한 문제를 해결했던 경험이 있습니다. 이로 인해 DEV00은 스프링에 대한 깊은 이해를 갖고 있으며, 이번 UI 문제를 능숙하게 해결할 것으로 기대됩니다. 또한, DEV00은 현재 할당된 이슈 중 하나인 'Password Reset Email Not Sent' 이슈의 우선순위가 낮기 때문에, 새로운 UI 이슈에 할당함으로써 현재 문제들을 순조롭게 진행할 수 있을 것입니다.\"\n}");
        Choice choice = new Choice(message, 0, "stop");
        choices.add(choice);

        chatGptResponseDTO = new ChatGptResponseDTO(
                "chatcmpl-9Uq1PRbDaWHbhPztysba55c56YI5x", "chat.completion", LocalDate.now(),
                "gpt-3.5-turbo-0125", choices
        );
    }

    @Test
    @DisplayName("selectUser: 실패 케이스 - Issue가 존재하지 않음")
    void testSelectUserFailureIssueNotFound() {
        // Mock behavior
        //when(issueService.getIssue(1L)).thenThrow(new RuntimeException("IssueEntity with id 1 not found."));
        Long notExistIssueId = 987654321L;

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> chatGptService.selectUser(notExistIssueId));
        assertEquals("can not find Issue because issue id " + notExistIssueId + " is null", runtimeException.getMessage());
    }

    @Test
    @DisplayName("makeQuestionStr: 성공 케이스. 반환된 값에 올바른 프롬프팅용 단어가 있는지 확인")
    void testMakeQuestionStrSuccess() {
        // Test method
        String questionStr = chatGptService.makeQuestionStr(relatedIssues, issueDTO, List.of("dev1"));

        // Verify
        assertTrue(questionStr.contains("currentIssue"));
        assertTrue(questionStr.contains("candidates"));
    }

    @Test
    @DisplayName("buildHttpEntity: 성공 케이스. GPT로 보내는 요청에 토큰이 올바르게 들어가 있는지 확인")
    void testBuildHttpEntitySuccess() {
        // Test data
        ChatGptRequestDTO requestDTO = new ChatGptRequestDTO();
        HttpEntity<ChatGptRequestDTO> entity = chatGptService.buildHttpEntity(requestDTO);

        // Verify
        assertNotNull(entity);
        HttpHeaders headers = entity.getHeaders();
        assertEquals(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE), headers.getContentType());
        assertTrue(headers.get("Authorization").get(0).contains(ChatGptConfig.BEARER));
    }

}
