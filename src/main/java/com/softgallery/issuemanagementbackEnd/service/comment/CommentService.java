package com.softgallery.issuemanagementbackEnd.service.comment;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements CommentServiceIF {
    private final CommentRepository commentRepository;
    private final JWTUtil jwtUtil;

    public CommentService(final CommentRepository commentRepository, final JWTUtil jwtUtil) {
        this.commentRepository = commentRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Boolean createComment(final CommentEntity commentDTO, String userToken, Long issueId) {
        // Token Parsing
        String tokenValue = JWTUtil.getOnlyToken(userToken);

        try {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setText(commentDTO.getText());
            commentEntity.setCreatedAt(LocalDateTime.now());
            commentEntity.setAuthorId(jwtUtil.getUserId(tokenValue));
            commentEntity.setIssueId(issueId);

            commentRepository.save(commentEntity);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public CommentEntity getComment(final Long id) throws ObjectNotFoundException {
        Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        return commentEntity
                .orElseThrow(() -> new ObjectNotFoundException("No such comment"));
    }

    @Override
    public List<CommentEntity> getCommentsInIssue(final Long issueId) {
        return commentRepository.findAllByIssueId(issueId);
    }

    @Override
    public Boolean updateComment(final CommentEntity newCommentEntity, final Long id) throws ObjectNotFoundException {
        Optional<CommentEntity> oldCommentEntity = commentRepository.findById(id);

        if(oldCommentEntity.isEmpty()) throw new ObjectNotFoundException("No such comment");

        try {
            CommentEntity comment = oldCommentEntity.get();
            comment.setText(newCommentEntity.getText());
            comment.setCreatedAt(newCommentEntity.getCreatedAt());
            comment.setAuthorId(newCommentEntity.getAuthorId());
            comment.setIssueId(newCommentEntity.getIssueId());
            commentRepository.save(comment);
            return true;
        } catch (IllegalArgumentException e) { return false; }
    }

    @Override
    public Boolean deleteComment(final Long id) {
        commentRepository.deleteById(id);
        return true;
    }
}
