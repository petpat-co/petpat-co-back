package com.smile.petpat.image.domain;

import com.smile.petpat.image.repository.ImageRepository;
import com.smile.petpat.post.category.domain.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageUploader {
    private final ImageRepository imageRepository;

    // 이미지 등록
    @Transactional
    public List<Image> savePostImage(List<Image> imageList){
        return imageRepository.saveAll(imageList);
    }

    public Image toImageEntity(String fakeFileName, String originalFileName, String filePath, Long postId, PostType postType){
        return new Image(originalFileName, fakeFileName, filePath, postId, postType);
    }

    // 로컬 이미지 삭제 (db)
    @Transactional
    public void deleteImg(Long postId, PostType postType) {
        imageRepository.deleteByPostIdAndPostType(postId, postType);
    }

    // 게시글 별 이미지 key 값 추출 메소드
    @Transactional
    public List<String> createKey(Long postId, PostType postType) {
        List<Image> images = imageRepository.findAllByPostIdAndPostType(postId, postType);
        return images.stream().map(Image::getFakeFileName).collect(Collectors.toList());
    }

    // 게시글 이미지 url 리스트 추출 method
    @Transactional
    public List<String> createImgList(Long postId, PostType postType) {
        List<Image> images = imageRepository.findAllByPostIdAndPostType(postId, postType);
        return images.stream().map(Image::getFilePath).collect(Collectors.toList());
    }
}
