package com.softgallery.issuemanagementbackEnd.dto.chat_gpt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
public class Message implements Serializable {
    private String role;
    private String content;

    public Message(String content) {
        this.role="user";
        this.content=content;
    }
}

