package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.ChatGptRequestDTO;
import com.softgallery.issuemanagementbackEnd.dto.chat_gpt.ChatGptResponseDTO;
import org.springframework.http.HttpEntity;

public interface ChatGptServiceIF {
    HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDto);
    ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDtoHttpEntity);

    ChatGptResponseDTO selectUser(Long issueId);
}
