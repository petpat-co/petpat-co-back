package com.smile.petpat.image.service;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.image.domain.Image;
import com.smile.petpat.image.dto.ImageResDto;
import com.smile.petpat.image.repository.ImageRepository;
import com.smile.petpat.image.util.ImageUtils;
import com.smile.petpat.image.util.S3Uploader;
import com.smile.petpat.post.category.domain.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Uploader s3Uploader;
    private final ImageUtils imageUtils;
    private final ImageRepository imageRepository;


    // Post 의 Image 등록
    @Transactional
    public void uploadPostImage(List<MultipartFile> multipartFiles, Long postId, PostType postType) {
        List<Image> imageList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            imageList.add(imageUtils.toImageEntity(postId, postType, multipartFile));
        }

        //ImageList 의 배열순서에 따라 우선순위 부여
        imageUtils.setPriority(imageList);

        imageRepository.saveAll(imageList);
    }


    // Post 의  Image 수정(삭제 및 등록)
    @Transactional
    public void updateImage(List<MultipartFile> newImages,List<Long> deletedImageIds,Long postId, PostType postType) {
        //삭제되거나 추가되는 이미지가 없으면 return
        //TODO: newImages에 아무것도 보내지 않았을 때 빈 Multipartfile이 1개 포함되는 문제
        //  우선 newImages.get(0)이 비어있는 지로 수정되는 이미지가 있는지 판별
        //  원인 파악 필요
        if(newImages.isEmpty()&&deletedImageIds.isEmpty()) return;
//        if(newImages.get(0).isEmpty() && deletedImageIds.isEmpty()) return;

        //삭제되는 이미지 삭제
        if(!deletedImageIds.isEmpty()) deleteImages(deletedImageIds);

        if(newImages.get(0).isEmpty()) return;

        //기존의 ImageList 에 추가되는 image 삽입
        List<Image> imageList =imageRepository.findAllByPostIdAndPostTypeOrderByPostId(postId, postType);
        for(MultipartFile newImage : newImages){
            imageList.add(imageUtils.toImageEntity(postId, postType, newImage));
        }

        //ImageList 의 배열순서에 따라 우선순위 부여
        imageUtils.setPriority(imageList);

        imageRepository.saveAll(imageList);

//        for(Image image : imageList){
//            if(image.getImageId() ==null) imageRepository.save(image);
//            else imageRepository.saveAndFlush(image);
//        }
    }


    // Post 삭제시 Post 에 해당하는 Image 도 삭제
    public void removePostImage(Long postId, PostType postType) {
        //해당 Post에 해당하는 Image의 Id 추출
        List<Long> deletedImageIds = imageRepository.findImageIdsByPostIdAndPostType(postId, postType);

        deleteImages(deletedImageIds);
    }

    // Post 의 Image 를 ImageResDto 로 반환
    @Transactional
    public List<ImageResDto> getImagesByPost(Long postId, PostType postType) {
        List<Image> images = imageRepository.findAllByPostIdAndPostTypeOrderByPostId(postId, postType);
        return images.stream()
                .map(image -> new ImageResDto(image.getImageId(),image.getFilePath()))
                .collect(Collectors.toList());
    }


    // ImageId 의 List 를 받아 Image 삭제(S3, DB)
    private void deleteImages(List<Long> deletedImageIds) {
        for(Long deletedImageId : deletedImageIds){
            String fakeFileName = imageRepository.findById(deletedImageId).orElseThrow(
                    ()-> new CustomException(ErrorCode.ILLEGAL_IMAGE_NOT_EXIST)
            ).getFakeFileName();

            s3Uploader.deleteImage(fakeFileName);

            imageRepository.deleteById(deletedImageId);
        }
    }
}
