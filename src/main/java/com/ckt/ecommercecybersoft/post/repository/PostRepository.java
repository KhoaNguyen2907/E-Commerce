package com.ckt.ecommercecybersoft.post.repository;

import com.ckt.ecommercecybersoft.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

}
