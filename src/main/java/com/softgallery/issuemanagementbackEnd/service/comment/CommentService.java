package com.softgallery.issuemanagementbackEnd.service.comment;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private CommentDTO entityToDTO(CommentEntity commentEntity) {
        return new CommentDTO(
                commentEntity.getCommentId(),
                commentEntity.getText(),
                commentEntity.getCreatedAt(),
                commentEntity.getAuthorId(),
                commentEntity.getIssueId()
        );
    }

    @Override
    public Boolean createComment(final CommentDTO commentDTO, String userToken, Long issueId) {
        // Token Parsing
        String tokenValue = JWTUtil.getOnlyToken(userToken);

        try {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setText(commentDTO.getText());
            commentEntity.setCreatedAt(LocalDateTime.now());
            commentEntity.setAuthorId(commentDTO.getAuthorId());
            commentEntity.setIssueId(commentDTO.getIssueId());

            commentRepository.save(commentEntity);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public CommentDTO getComment(final Long id) throws ObjectNotFoundException {
        Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        if(commentEntity.isEmpty()) throw new ObjectNotFoundException("No such comment");

        return this.entityToDTO(commentEntity.get());
    }

    @Override
    public List<CommentDTO> getCommentsInIssue(final Long issueId) {
        List<CommentEntity> commentEntities = commentRepository.findAllByIssueId(issueId);

        ArrayList<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntities) {
            commentDTOList.add(this.entityToDTO(commentEntity));
        }

        return commentDTOList;
    }

    @Override
    public Boolean updateComment(final CommentDTO commentDTO, final Long id) throws ObjectNotFoundException {
        Optional<CommentEntity> commentEntity = commentRepository.findById(id);

        if(commentEntity.isEmpty()) throw new ObjectNotFoundException("No such comment");

        try {
            CommentEntity comment = commentEntity.get();
            comment.setText(commentDTO.getText());
            comment.setCreatedAt(commentDTO.getCreatedAt());
            comment.setAuthorId(commentDTO.getAuthorId());
            comment.setIssueId(commentDTO.getIssueId());
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
