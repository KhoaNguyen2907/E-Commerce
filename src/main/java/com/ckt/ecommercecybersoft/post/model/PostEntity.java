package com.ckt.ecommercecybersoft.post.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PostEntity {

    @UtilityClass
    public static class Post {
        public static final String TABLE_NAME = "post";
        public static final String TITLE = "E_TITLE";
        public static final String SUBTITLE = "E_SUBTITLE";
        public static final String CODE = "E_CODE";
        public static final String CONTENT = "E_CONTENT";
    }

    @UtilityClass
    public static class Comment {
        public static final String TABLE_NAME = "comment";
        public static final String COMMENT = "E_COMMENT";
    }

    @UtilityClass
    public static class CommentMappedPost {
        public static final String POST_MAPPED_COMMENT = "post";
        public static final String JOIN_TABLE_POST_ID = "E_POST_ID";
    }

    @UtilityClass
    public static class UserMappedPost {
        public static final String USER_MAPPED_POST = "user";
        public static final String JOIN_TABLE_USER_ID = "E_USER_ID";
    }

    @UtilityClass
    public static class UserMappedComment {
        public static final String USER_MAPPED_COMMENT = "user";
        public static final String JOIN_TABLE_USER_ID = "E_USER_ID";
    }
}
