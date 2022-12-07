package com.ckt.ecommercecybersoft.post.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.post.dto.CommentDTO;
import com.ckt.ecommercecybersoft.post.dto.PostDTO;
import com.ckt.ecommercecybersoft.post.model.Comment;
import com.ckt.ecommercecybersoft.post.model.Post;
import com.ckt.ecommercecybersoft.post.repository.CommentRepository;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.UUID;

public interface CommentService extends GenericService<Comment, CommentDTO, UUID> {
    CommentDTO updateComment(CommentDTO commentDTO, UUID id);
    CommentDTO addComment(CommentDTO commentDTO);
}

@Service
@Transactional
class CommentServiceImpl implements CommentService {
    private final ProjectMapper mapper;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public CommentServiceImpl (ProjectMapper mapper, CommentRepository commentRepository, PostService postService, UserService userService) {
        this.mapper = mapper;
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public JpaRepository<Comment, UUID> getRepository() {
        return this.commentRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return this.mapper;
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, UUID id) {
        Comment curComment = commentRepository.findById(id)
                .map(comment -> {
                    comment.setCmt(commentDTO.getCmt());
                    return commentRepository.save(comment);
                }).orElseGet(() -> {
                    commentDTO.setId(id);
                    return commentRepository.save(mapper.map(commentDTO, Comment.class));
                });
        return mapper.map(curComment, CommentDTO.class);
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        Comment comment = mapper.map(commentDTO, Comment.class);
        Post post = postService.findById(commentDTO.getPostId()).orElseThrow(() -> new ValidationException("Post id not existed!"));
        post.addComment(comment);
        User user = userService.findById(commentDTO.getUserId()).orElseThrow(() -> new ValidationException("User is not existed!"));
//        user.addComment(comment);
        postService.save(mapper.map(post, PostDTO.class), Post.class);
        commentRepository.save(comment);
        return commentDTO;
    }
}
