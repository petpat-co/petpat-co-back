package com.smile.petpat.post.qna.domain;

import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class QnaCommand {
    private String title;
    private String content;
    private User user;
    private List<MultipartFile> images;

    public QnaCommand(){

    }
    public QnaCommand(String title, String content, User user, List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.images = images;
    }

    public Qna toRegisterEntity(User user){
        return Qna.builder()
                .title(title)
                .content(content)
                .user(user)
                .postType(PostType.QNA)
                .build();
    }

    public Qna toUpdateEntity(User user, Long postId) {
        return Qna.builder()
                .qnaId(postId)
                .user(user)
                .title(title)
                .content(content)
                .postType(PostType.QNA)
                .build();


    }

}
