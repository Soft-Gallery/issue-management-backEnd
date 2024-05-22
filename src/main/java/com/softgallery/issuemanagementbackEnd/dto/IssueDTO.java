package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.entity.*;

import java.util.List;

import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import lombok.Getter;

@Getter
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private UserDTO reporter;
    private State status;
    private Priority priority;
    private UserDTO assignee;
    private UserDTO fixer;
    private Long projectId;
    private List<CommentEntity> comments;

    public IssueDTO() { }

    public IssueDTO(final Long id, final String title, final String description, final UserDTO reporter,
                    final State status, final Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
    }

    public IssueDTO(final Long id, final String title, final String description, final UserDTO reporter,
                    final State status, final Priority priority,
                    final UserDTO assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
    }

    public IssueDTO(final Long id, final String title, final String description, final UserDTO reporter,
                    final State status, final Priority priority,
                    final UserDTO assignee, final List<CommentEntity> comments, final UserDTO fixer, final Long projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
        this.comments = comments;
        this.fixer = fixer;
        this.projectId = projectId;
    }
}
