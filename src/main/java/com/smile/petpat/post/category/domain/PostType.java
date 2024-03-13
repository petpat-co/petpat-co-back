package com.smile.petpat.post.category.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PostType {
    QNA("QNA"),
    REHOMING("REHOMING"),
    TRADE("TRADE"),

    ;
    private final String description;

    private static final Map<String, PostType> POST_TYPE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(PostType::getDescription, Function.identity())));

    PostType(String description) {
        this.description = description;
    }

    @JsonCreator
    public static PostType find(String description) {
        return Optional.ofNullable(description)
                .map(POST_TYPE_MAP::get)
                .orElseThrow(() -> new IllegalArgumentException("게시글 타입을 찾을 수 없습니다.."));
    }
    @JsonValue
    public String getDescription(){
        return description;
    }




}
