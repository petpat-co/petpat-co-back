package com.smile.petpat.image.domain;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Component
public class ImageUtils {
    private static final Set<String> VALID_EXTENSIONS = new HashSet<>(Arrays.asList("gif", "png", "jpg", "jpeg",
                                                                                    "GIF", "PNG", "JPG", "JPEG"));

    /* 파일명 난수화 */
    public String generateRandomFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    /* 파일 확장명 체크 로직 */
    public String getFileExtension(String fileName) {
        try {
            int idx = fileName.lastIndexOf(".");
            if (idx == -1 || idx == fileName.length() - 1) {
                throw new CustomException(ErrorCode.WRONG_TYPE_IMAGE);
            }

            String extension = fileName.substring(idx + 1);

            if (!VALID_EXTENSIONS.contains(extension)) {
                throw new CustomException(ErrorCode.WRONG_TYPE_IMAGE);
            }
            return "." + extension;
        }
        catch (IndexOutOfBoundsException e) {
            throw new CustomException(ErrorCode.WRONG_TYPE_IMAGE);
        }
    }
}
