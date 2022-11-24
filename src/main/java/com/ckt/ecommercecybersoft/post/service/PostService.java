package com.ckt.ecommercecybersoft.post.service;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.post.dto.PostDTO;
import com.ckt.ecommercecybersoft.post.model.Post;
import com.ckt.ecommercecybersoft.post.repository.PostRepository;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.service.UserService;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.UUID;

public interface PostService extends GenericService<Post, PostDTO, UUID> {

    PostDTO updatePost(PostDTO post, UUID uuid);

    PostDTO createPost(PostDTO postDTO);
}

@Service
@Transactional
class PostServiceImpl implements PostService {
    @Autowired
    private UserService userService;

    private final PostRepository postRepository;
    private final UserService userService;
    private final ProjectMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ProjectMapper mapper, UserService userService) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, UUID id) {
        Post curPost = postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postDTO.getTitle());
                    post.setSubtitle(postDTO.getSubtitle());
                    post.setCode(postDTO.getCode());
                    post.setContent(postDTO.getContent());
                    return postRepository.save(post);
                }).orElseGet(() -> {
                    postDTO.setId(id);
                    return postRepository.save(mapper.map(postDTO, Post.class));
                });
        return mapper.map(curPost, PostDTO.class);
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapper.map(postDTO, Post.class);
        UserDto currentUserDto = userService.getCurrentUser().orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        User currentUser = mapper.map(currentUserDto, User.class);
        post.setUser(currentUser);
        post = postRepository.save(post);
        return mapper.map(post, PostDTO.class);
    }

    @Override
    public JpaRepository<Post, UUID> getRepository() {
        return this.postRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return this.mapper;
    }
}
