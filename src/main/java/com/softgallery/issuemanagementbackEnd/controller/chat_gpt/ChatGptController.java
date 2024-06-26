package com.softgallery.issuemanagementbackEnd.controller.chat_gpt;

import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.ChatGptResponseDTO;
import com.softgallery.issuemanagementbackEnd.service.chatGpt.ChatGptServiceIF;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/gpt")
@ResponseBody
public class ChatGptController {
    private final ChatGptServiceIF chatGptService;

    public ChatGptController(ChatGptServiceIF chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping("/recommendation/{issueId}")
    public Map<String, String> selectUser(@PathVariable("issueId") Long issueId) {
        return this.chatGptService.selectUser(issueId);
    }
}
