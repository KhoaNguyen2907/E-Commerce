package com.ckt.ecommercecybersoft.post.controller;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.DateTimeUtils;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.post.dto.PostDTO;
import com.ckt.ecommercecybersoft.post.model.Post;
import com.ckt.ecommercecybersoft.post.service.PostService;
import com.ckt.ecommercecybersoft.post.utils.PostUrlUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(PostUrlUtils.POST_API_V1)
public class PostController {
    private final ProjectMapper postMapper;
    private final PostService postService;

    public PostController(ProjectMapper postMapper, PostService postService) {
        this.postMapper = postMapper;
        this.postService = postService;
    }

    @GetMapping(PostUrlUtils.GET_ALL)
    public ResponseEntity<ResponseDTO> getAllPostDto(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        List<PostDTO> posts = postService.findAllDto(Pageable.ofSize(pageSize).withPage(pageNumber - 1), PostDTO.class);
        return ResponseUtils.get(posts, HttpStatus.OK);
    }

    @GetMapping(PostUrlUtils.GET_ALL_WITH_PAGING)
    public ResponseEntity<ResponseDTO> getAllPostDtoPaging(@RequestParam(value = "size", defaultValue = "1") int size, @RequestParam(value = "index", defaultValue = "0") int index) {
        return ResponseUtils.get(postService.findAllDto(Pageable.ofSize(size).withPage(index), PostDTO.class), HttpStatus.OK);
    }

    @GetMapping(PostUrlUtils.GET_BY_ID)
    public ResponseEntity<ResponseDTO> getPostById(@PathVariable("id") UUID id) {
        Post post = postService.findById(id).orElseThrow(() -> new NotFoundException("099 Post not found!"));
        return ResponseUtils.get(postMapper.map(post, PostDTO.class), HttpStatus.OK);
    }

    @PostMapping(PostUrlUtils.ADD_POST)
    public ResponseEntity<ResponseDTO> addPost(@RequestBody @Valid PostDTO postDTO) {
        //split string to code
        String[] arr = postDTO.getTitle().split(" ");
        StringBuilder code = new StringBuilder();
        for (String s: arr) {
            code.append(s);
            code.append("-");
        }
        code.append(DateTimeUtils.now());
        postDTO.setCode(code.toString());

        PostDTO savedPost = postService.createPost(postDTO);

        return ResponseUtils.get(savedPost, HttpStatus.CREATED);
    }

    @PutMapping(PostUrlUtils.UPDATE_POST)
    public ResponseEntity<ResponseDTO> updatePost(@RequestBody @Valid PostDTO postDTO, @PathVariable("id") UUID id) {
        return ResponseUtils.get(postService.updatePost(postDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(PostUrlUtils.DELETE_POST)
    public ResponseEntity<Object> deletePost(@PathVariable("id") UUID id) {
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
