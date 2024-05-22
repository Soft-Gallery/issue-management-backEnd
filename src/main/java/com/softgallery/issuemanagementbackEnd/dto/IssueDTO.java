package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.entity.DeveloperEntity;
import com.softgallery.issuemanagementbackEnd.entity.TesterEntity;
import java.util.List;

import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import lombok.Getter;

@Getter
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private UserEntity reporter;
    private String status;
    private String priority;
    private UserEntity assignee;
    private UserEntity fixer;
    private Long projectId;
    private List<CommentEntity> comments;

    public IssueDTO() { }

    public IssueDTO(final Long id, final String title, final String description, final TesterEntity reporter,
                    final String status, final String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
    }

    public IssueDTO(final Long id, final String title, final String description, final TesterEntity reporter,
                    final String status, final String priority,
                    final DeveloperEntity assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
    }

    public IssueDTO(final Long id, final String title, final String description, final TesterEntity reporter,
                    final String status, final String priority,
                    final DeveloperEntity assignee, final List<CommentEntity> comments, final UserEntity fixer, final Long projectId) {
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
