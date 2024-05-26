package com.softgallery.issuemanagementbackEnd.dto;

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

    Message(String content) {
        this.role="system";
        this.content=content;
    }
}

