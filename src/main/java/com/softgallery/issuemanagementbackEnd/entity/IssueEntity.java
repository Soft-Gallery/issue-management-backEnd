package com.softgallery.issuemanagementbackEnd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Long reporterId;

    @NonNull
    private String status;

    @NonNull
    private String priority;

    @NonNull
    private Long assigneeId;

    @NonNull
    private Long fixerId;

    @NonNull
    private Long projectId;
}
