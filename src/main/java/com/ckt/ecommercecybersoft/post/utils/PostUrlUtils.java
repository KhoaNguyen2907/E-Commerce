package com.ckt.ecommercecybersoft.post.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PostUrlUtils {
    public static final String POST_API_V1 = "/api/v1/post";
    public static final String GET_ALL = "/get-all-post";
    public static final String GET_ALL_WITH_PAGING = "/get-all-post-with-paging";
    public static final String GET_BY_ID = "/get-post-by-id/{id}";
    public static final String ADD_POST = "/add-post";
    public static final String UPDATE_POST = "/update-post/{id}";
    public static final String DELETE_POST = "/delete-post/{id}";
}
