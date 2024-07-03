package com.smile.petpat.image.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.image.util.ImageUtils;
import com.smile.petpat.post.category.domain.PostType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.smile.petpat.common.response.ErrorCode.FAILED_UPLOAD_IMAGE;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private  final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private  String bucket;

    public String uploadImage(MultipartFile file, String fakeFileName){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fakeFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new CustomException(FAILED_UPLOAD_IMAGE);
        }

        return amazonS3.getUrl(bucket, fakeFileName).toString();
    }


    // S3 이미지 삭제 s3
    public void deleteImage(String fileName){
        amazonS3.deleteObject(bucket, fileName);
    }

}