package com.softgallery.issuemanagementbackEnd.dto.issue;

import com.softgallery.issuemanagementbackEnd.dto.comment.CommentDTO;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

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
    private List<CommentDTO> comments;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate = LocalDateTime.of(1950, 6, 25, 0, 0, 0);

    public IssueDTO() { }

    public IssueDTO(final Long id, final String title, final String description, final UserDTO reporter,
                    final State status, final Priority priority, final LocalDateTime startDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.startDate = startDate;
    }

    public IssueDTO(final Long id, final String title, final String description, final UserDTO reporter,
                    final State status, final Priority priority,
                    final UserDTO assignee, final LocalDateTime startDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
        this.startDate = startDate;
    }

    public IssueDTO(final Long id, final String title, final String description, final UserDTO reporter,
                    final State status, final Priority priority,
                    final UserDTO assignee, final List<CommentDTO> comments,
                    final UserDTO fixer, final Long projectId,
                    final LocalDateTime startDate) {
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
        this.startDate = startDate;
    }

    public IssueDTO(final Long id, final String title, final String description, final UserDTO reporter,
                    final State status, final Priority priority,
                    final UserDTO assignee, final List<CommentDTO> comments,
                    final UserDTO fixer, final Long projectId,
                    final LocalDateTime startDate, final LocalDateTime endDate) {
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
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
