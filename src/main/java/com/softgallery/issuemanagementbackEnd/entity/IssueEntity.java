package com.softgallery.issuemanagementbackEnd.entity;

import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "issue")
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private String reporterId;

    @Enumerated(EnumType.STRING)
    @NonNull
    private State status;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Priority priority;

    @Nullable
    private String assigneeId;

    @Nullable
    private String fixerId;

    @NonNull
    private Long projectId;
}
