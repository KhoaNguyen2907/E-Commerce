package com.ckt.ecommercecybersoft.post.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.post.dto.CommentDTO;
import com.ckt.ecommercecybersoft.post.model.Comment;
import com.ckt.ecommercecybersoft.post.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CommentService extends GenericService<Comment, CommentDTO, UUID> {
    CommentDTO updateComment(CommentDTO commentDTO, UUID id);
}

@Service
@Transactional
class CommentServiceImpl implements CommentService {
    private final ProjectMapper mapper;
    private final CommentRepository commentRepository;

    public CommentServiceImpl (ProjectMapper mapper, CommentRepository commentRepository) {
        this.mapper = mapper;
        this.commentRepository = commentRepository;
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
}
