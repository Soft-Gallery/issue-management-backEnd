package com.softgallery.issuemanagementbackEnd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptRequestDTO implements Serializable {

    private String model="gpt-3.5-turbo";
    private List<Message> messages;
    @JsonProperty("max_tokens")
    private Integer maxTokens=4000;
    private Double temperature=1.0;
    @JsonProperty("top_p")
    private Double topP=1.0;

    @Builder
    public ChatGptRequestDTO(String model, List<Message> messages,
                             Integer maxTokens, Double temperature,
                             Double topP) {
        this.messages=messages;
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.topP = topP;
    }
}