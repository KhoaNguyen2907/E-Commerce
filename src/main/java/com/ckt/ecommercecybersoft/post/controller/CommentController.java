package com.ckt.ecommercecybersoft.post.controller;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.post.dto.CommentDTO;
import com.ckt.ecommercecybersoft.post.model.Comment;
import com.ckt.ecommercecybersoft.post.service.CommentService;
import com.ckt.ecommercecybersoft.post.utils.CommentUrlUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(CommentUrlUtils.COMMENT_API_V1)
public class CommentController {
    private final ProjectMapper mapper;
    private final CommentService commentService;

    public CommentController(ProjectMapper mapper, CommentService commentService) {
        this.mapper = mapper;
        this.commentService = commentService;
    }

    @GetMapping(CommentUrlUtils.GET_ALL)
    public ResponseEntity<ResponseDTO> getAllCommentDto() {
        return ResponseUtils.get(commentService.findAllDto(CommentDTO.class), HttpStatus.OK);
    }

    @GetMapping(CommentUrlUtils.GET_ALL_WITH_PAGING)
    public ResponseEntity<ResponseDTO> getAllCommentDtoPaging(@RequestParam(value = "size", defaultValue = "1") int size, @RequestParam(value = "index", defaultValue = "0") int index) {
        return ResponseUtils.get(commentService.findAllDto(Pageable.ofSize(size).withPage(index), CommentDTO.class), HttpStatus.OK);
    }

    @GetMapping(CommentUrlUtils.GET_BY_ID)
    public ResponseEntity<ResponseDTO> getCommentById(@PathVariable("id") UUID id) {
        Comment comment = commentService.findById(id).orElseThrow(() -> new NotFoundException("Comment not found!"));
        return ResponseUtils.get(mapper.map(comment, CommentDTO.class), HttpStatus.OK);
    }

    @PostMapping(CommentUrlUtils.ADD_COMMENT)
    public ResponseEntity<ResponseDTO> addComment(@RequestBody @Valid CommentDTO commentDTO) {
        return ResponseUtils.get(commentService.addComment(commentDTO), HttpStatus.CREATED);
    }

    @PutMapping(CommentUrlUtils.UPDATE_COMMENT_BY_ID)
    public ResponseEntity<ResponseDTO> updateComment(@PathVariable("id") UUID id, @RequestBody @Valid CommentDTO commentDTO) {
        return ResponseUtils.get(commentService.updateComment(commentDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(CommentUrlUtils.DELETE_COMMENT)
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable("id") UUID id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
