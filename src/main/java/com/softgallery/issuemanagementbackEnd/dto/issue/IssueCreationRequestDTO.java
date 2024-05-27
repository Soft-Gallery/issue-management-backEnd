package com.softgallery.issuemanagementbackEnd.dto.issue;

import com.softgallery.issuemanagementbackEnd.dto.comment.CommentDTO;
import lombok.Getter;

@Getter
public class IssueCreationRequestDTO {
    private IssueDTO issue;
    private CommentDTO comment;

    public IssueCreationRequestDTO() {};
}
