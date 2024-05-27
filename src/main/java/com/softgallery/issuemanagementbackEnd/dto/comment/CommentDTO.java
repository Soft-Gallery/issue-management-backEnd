package com.softgallery.issuemanagementbackEnd.dto.comment;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private String authorId;
    private Long issueId;

    public CommentDTO() { }

    public CommentDTO(final Long id, final String text, final LocalDateTime createdAt, final String authorId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.authorId = authorId;
    }

    public CommentDTO(final Long id, final String text, final LocalDateTime createdAt, final String authorId,
                      final Long issueId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.issueId = issueId;
    }
}
