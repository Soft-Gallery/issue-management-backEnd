package com.softgallery.issuemanagementbackEnd.controller.chatGpt;

import com.softgallery.issuemanagementbackEnd.dto.ChatGptRequestDTO;
import com.softgallery.issuemanagementbackEnd.dto.ChatGptResponseDTO;
import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.QuestionRequestDTO;
import com.softgallery.issuemanagementbackEnd.service.chatGpt.ChatGptServiceIF;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gpt")
@ResponseBody
public class ChatGptController {
    private final ChatGptServiceIF chatGptService;

    public ChatGptController(ChatGptServiceIF chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping("/recommendation/{issueId}")
    public ChatGptResponseDTO selectUser(@PathVariable("issueId") Long issueId) {
        return this.chatGptService.selectUser(issueId);
    }
}
