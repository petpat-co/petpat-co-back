package com.smile.petpat.post.qna.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class QnaDto {

    @Getter
    @Setter
    @ToString
    public static class CommonQna{

        @NotBlank(message = "제목은 필수입니다.")
        private String title;
        @NotBlank(message = "내용은 필수입니다.")
        private String content;

        private List<MultipartFile> images;

        public CommonQna(){

        }
        public CommonQna(String title, String content, List<MultipartFile> images) {
            this.title = title;
            this.content = content;
            this.images = images;
        }

        public QnaCommand toCommand(){
            return QnaCommand.builder()
                    .title(title)
                    .content(content)
                    .images(images)
                    .build();
        }
    }
}
