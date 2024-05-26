package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.service.chatGpt.GptPrompt;
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
        Message prompMessage = new Message(GptPrompt.getPromptVer1());
        Message questionMessage = new Message(s);

        this.messages.add(prompMessage);
        this.messages.add(questionMessage);
    }
}