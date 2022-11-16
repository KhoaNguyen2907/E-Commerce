package com.ckt.ecommercecybersoft.post.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentUrlUtils {
    public static final String COMMENT_API_V1 = "api/v1/comment";
    public static final String GET_ALL = "/get-all-comment";
    public static final String GET_ALL_WITH_PAGING = "/get-all-comment-with-paging";
    public static final String GET_BY_ID = "/get-comment-by-id/{id}";
    public static final String ADD_COMMENT = "/add-comment";
    public static final String UPDATE_COMMENT = "/update-comment/{id}";
    public static final String DELETE_COMMENT = "/delete-comment/{id}";
}
