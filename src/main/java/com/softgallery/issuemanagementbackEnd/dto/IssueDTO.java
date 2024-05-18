package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.entity.DeveloperEntity;
import com.softgallery.issuemanagementbackEnd.entity.TesterEntity;
import java.util.List;
import lombok.Getter;

@Getter
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private TesterEntity reporter;
    private String status;
    private String priority;
    private DeveloperEntity assignee;
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
                    final DeveloperEntity assignee, final List<CommentEntity> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
        this.comments = comments;
    }
}
