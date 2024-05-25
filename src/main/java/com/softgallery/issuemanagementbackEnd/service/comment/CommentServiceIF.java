package com.softgallery.issuemanagementbackEnd.service.comment;

import com.softgallery.issuemanagementbackEnd.dto.CommentDTO;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import java.util.List;

public interface CommentServiceIF {
    Boolean createComment(CommentDTO commentDTO, String userToken, Long userId);
    CommentDTO getComment(Long id) throws ObjectNotFoundException;
    List<CommentDTO> getCommentsInIssue(Long issueId);
    Boolean updateComment(CommentDTO commentDTO, Long id) throws ObjectNotFoundException;
    Boolean deleteComment(Long id);
}
