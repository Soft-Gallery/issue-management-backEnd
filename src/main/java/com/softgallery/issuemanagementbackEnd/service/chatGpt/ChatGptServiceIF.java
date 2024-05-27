package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import com.softgallery.issuemanagementbackEnd.dto.ChatGptRequestDTO;
import com.softgallery.issuemanagementbackEnd.dto.ChatGptResponseDTO;
import com.softgallery.issuemanagementbackEnd.dto.QuestionRequestDTO;
import org.springframework.http.HttpEntity;

public interface ChatGptServiceIF {
    HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDto);
    ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDtoHttpEntity);

    ChatGptResponseDTO selectUser(Long issueId);
}
