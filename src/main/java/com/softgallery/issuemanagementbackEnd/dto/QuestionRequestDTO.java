package com.softgallery.issuemanagementbackEnd.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestionRequestDTO implements Serializable {
    private final String model="gpt-3.5-turbo";
    private final Long maxTokens=4000L;
    private List<Message> messages;
    private final double temperature=1.0;
    private final double topP=1.0;

    public QuestionRequestDTO(){}
    public QuestionRequestDTO(String s){
        this.messages=new ArrayList<Message>();
        Message message = new Message(s);
        this.messages.add(message);
    }
}