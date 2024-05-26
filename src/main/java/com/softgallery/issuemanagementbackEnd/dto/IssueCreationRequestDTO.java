package com.softgallery.issuemanagementbackEnd.dto;

import lombok.Getter;

@Getter
public class IssueCreationRequestDTO {
    private IssueDTO issue;
    private CommentDTO comment;

    public IssueCreationRequestDTO() {};
}
