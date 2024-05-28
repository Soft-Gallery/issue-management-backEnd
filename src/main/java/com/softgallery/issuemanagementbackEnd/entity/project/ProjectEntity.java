package com.softgallery.issuemanagementbackEnd.entity.project;

import com.softgallery.issuemanagementbackEnd.service.project.ProjectState;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ProjectState projectState;

    @NonNull
    private String description;

    @NonNull
    private String adminId;
}