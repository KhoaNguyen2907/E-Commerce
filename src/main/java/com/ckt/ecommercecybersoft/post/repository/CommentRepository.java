package com.ckt.ecommercecybersoft.post.repository;

import com.ckt.ecommercecybersoft.post.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
