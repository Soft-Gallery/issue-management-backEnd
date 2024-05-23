package com.softgallery.issuemanagementbackEnd.controller.comment;

import com.softgallery.issuemanagementbackEnd.entity.CommentEntity;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.service.comment.CommentServiceIF;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/comment")
public class CommentController {
    private final CommentServiceIF commentService;

    public CommentController(final CommentServiceIF commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/new/{issueId}")
    public Boolean createComment(@RequestBody CommentEntity commentEntity,
                                 @RequestHeader(name = "Authorization") String userToken,
                                 @PathVariable Long issueId) {
        return commentService.createComment(commentEntity, userToken, issueId);
    }

    @PostMapping("/get/{id}")
    public CommentEntity getComment(@PathVariable Long id) throws ObjectNotFoundException {
        return commentService.getComment(id);
    }

    @PostMapping("/get-list/{issueId}")
    public List<CommentEntity> getCommentsInIssue(@PathVariable Long issueId) {
        return commentService.getCommentsInIssue(issueId);
    }

    @PostMapping("/update/{id}")
    public Boolean updateComment(@RequestBody CommentEntity commentEntity, @PathVariable Long id) throws ObjectNotFoundException {
        return commentService.updateComment(commentEntity, id);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}
