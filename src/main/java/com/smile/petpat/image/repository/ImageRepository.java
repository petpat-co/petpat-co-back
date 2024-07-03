package com.smile.petpat.image.repository;

import com.smile.petpat.image.domain.Image;
import com.smile.petpat.post.category.domain.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findAllByPostIdAndPostTypeOrderByPostId(Long postId, PostType postType);
    @Query("SELECT i.imageId FROM Image i WHERE i.postId = :postId AND i.postType = :postType")
    List<Long> findImageIdsByPostIdAndPostType(@Param("postId")Long postId,
                                          @Param("postType")PostType postType);  //해당 Post에 해당하는 Image들의 Id 추출
}
