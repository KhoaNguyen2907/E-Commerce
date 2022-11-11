package com.ckt.ecommercecybersoft.post.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.post.dto.PostDTO;
import com.ckt.ecommercecybersoft.post.model.Post;
import com.ckt.ecommercecybersoft.post.repository.PostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PostService extends GenericService<Post, PostDTO, UUID> {

    PostDTO updatePost(PostDTO post, UUID uuid);

}

@Service
@Transactional
class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final ProjectMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ProjectMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
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
    public JpaRepository<Post, UUID> getRepository() {
        return this.postRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return this.mapper;
    }
}
