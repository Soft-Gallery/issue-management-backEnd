package com.softgallery.issuemanagementbackEnd.service.comment;

import com.softgallery.issuemanagementbackEnd.dto.comment.CommentDTO;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import java.util.List;

public interface CommentServiceIF {
    Boolean createComment(CommentDTO commentDTO, String token, Long issueId);

    CommentDTO getComment(Long id) throws ObjectNotFoundException;
    List<CommentDTO> getCommentsInIssue(Long issueId);
    Boolean updateComment(CommentDTO commentDTO, Long id) throws ObjectNotFoundException;
    Boolean deleteComment(Long id);
}
