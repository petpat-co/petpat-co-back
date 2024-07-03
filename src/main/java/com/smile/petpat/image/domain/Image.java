package com.smile.petpat.image.domain;

import com.smile.petpat.post.category.domain.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "TB_IMAGE")
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name = "FAKE_FILE_NAME")
    private String fakeFileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "POST_ID")
    private Long postId;

    @Column(name = "POST_TYPE")
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(name = "IMAGE_PRIORITY")
    private ImagePriority priority;

    public Image(String originalFileName, String fakeFileName, String filePath, Long postId, PostType postType) {
        this.originalFileName = originalFileName;
        this.fakeFileName = fakeFileName;
        this.filePath = filePath;
        this.postId = postId;
        this.postType = postType;
    }

    public Image setImagePriority(ImagePriority imagePriority){
        this.priority = imagePriority;
        return this;
    }
}
