package com.ckt.ecommercecybersoft.post.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PostEntity {

    @UtilityClass
    public class Post {
        public static final String TABLE_NAME = "post";
        public static final String TITLE = "title";
        public static final String SUBTITLE = "subtitle";
        public static final String CODE = "code";
        public static final String CONTENT = "content";
    }
}
