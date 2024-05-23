package com.softgallery.issuemanagementbackEnd.service.comment;

import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import java.util.List;

public interface CommentServiceIF {
    Boolean createComment(CommentEntity commentEntity, String userToken, Long userId);
    CommentEntity getComment(Long id) throws ObjectNotFoundException;
    List<CommentEntity> getCommentsInIssue(Long issueId);
    Boolean updateComment(CommentEntity commentEntity, Long id) throws ObjectNotFoundException;
    Boolean deleteComment(Long id);
}
