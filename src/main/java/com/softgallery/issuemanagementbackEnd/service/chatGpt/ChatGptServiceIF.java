package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.ChatGptRequestDTO;
import org.springframework.http.HttpEntity;

import java.util.Map;

public interface ChatGptServiceIF {
    HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDto);
    Map<String, String> getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDtoHttpEntity);

    Map<String, String> selectUser(Long issueId);
}
