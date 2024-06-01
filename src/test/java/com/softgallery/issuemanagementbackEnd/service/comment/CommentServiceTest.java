package com.softgallery.issuemanagementbackEnd.service.comment;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.comment.CommentDTO;
import com.softgallery.issuemanagementbackEnd.entity.comment.CommentEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.comment.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    void testCreateComment() {
        // given
        CommentDTO commentDTO = new CommentDTO(null, "Test Comment", LocalDateTime.now(), "author123", 1L);
        String token = "Bearer token";
        String userId = "user123";

        when(jwtUtil.getUserId(any())).thenReturn(userId);

        // when
        Boolean result = commentService.createComment(commentDTO, token, 1L);

        // then
        assertTrue(result);
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    @DisplayName("특정 댓글 가져오기 테스트")
    void testGetComment() throws ObjectNotFoundException {
        // given
        Long commentId = 1L;
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(commentId);
        commentEntity.setText("Test Comment");
        commentEntity.setCreatedAt(LocalDateTime.now());
        commentEntity.setAuthorId("author123");
        commentEntity.setIssueId(1L);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));

        // when
        CommentDTO result = commentService.getComment(commentId);

        // then
        assertNotNull(result);
        assertEquals("Test Comment", result.getText());
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    @DisplayName("Issue의 모든 댓글 가져오기 테스트")
    void testGetCommentsInIssue() {
        // given
        Long issueId = 1L;
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(1L);
        commentEntity.setText("Test Comment");
        commentEntity.setCreatedAt(LocalDateTime.now());
        commentEntity.setAuthorId("author123");
        commentEntity.setIssueId(issueId);

        List<CommentEntity> commentEntities = new ArrayList<>();
        commentEntities.add(commentEntity);

        when(commentRepository.findAllByIssueId(issueId)).thenReturn(commentEntities);

        // when
        List<CommentDTO> result = commentService.getCommentsInIssue(issueId);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commentRepository, times(1)).findAllByIssueId(issueId);
    }

    @Test
    @DisplayName("댓글 업데이트 테스트")
    void testUpdateComment() throws ObjectNotFoundException {
        // given
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO(commentId, "Updated Comment", LocalDateTime.now(), "author123", 1L);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));

        // when
        Boolean result = commentService.updateComment(commentDTO, commentId);

        // then
        assertTrue(result);
        assertEquals("Updated Comment", commentEntity.getText());
        verify(commentRepository, times(1)).save(commentEntity);
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void testDeleteComment() {
        // given
        Long commentId = 1L;

        // when
        Boolean result = commentService.deleteComment(commentId);

        // then
        assertTrue(result);
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
