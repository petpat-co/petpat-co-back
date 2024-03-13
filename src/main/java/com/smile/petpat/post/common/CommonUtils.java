package com.smile.petpat.post.common;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.bookmarks.domain.Bookmark;
import com.smile.petpat.post.common.bookmarks.repository.BookmarkRepository;
import com.smile.petpat.post.common.likes.domain.Likes;
import com.smile.petpat.post.common.likes.repository.LikesRepository;
import com.smile.petpat.post.rehoming.service.RehomingServiceImpl;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommonUtils {

    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final BookmarkRepository bookmarkRepository;

    // 게시글 좋아요 값 유무 확인
    public Boolean LikePostChk(Long postId, PostType postType, String userEmail){
        return getLikePost(postId, postType, userEmail) != null;
    }

    // 게시글 좋아요 조회
    public Likes getLikePost(Long postId, PostType postType, String userEmail){
        User user = userChk(userEmail);
        return likesRepository.findUserLikeQuery(postId, postType.toString(), user.getId());
    }

    // 게시글 좋아요 삭제
    public void delLikes(Long postId, String postType, String userEmail) {
        User user = userChk(userEmail);
        likesRepository.deleteByUser_UserIdAndPost_PostIdAndPostType(postId, postType, user.getId());
    }

    // 게시글 북마크 값 유무 확인
    public Boolean BookmarkPostChk(Long postId, PostType postType, String userEmail) {
        return getBookmarkPost(postId, postType, userEmail) !=null;
    }

    // 게시글 북마크 조회
    public Bookmark getBookmarkPost(Long postId, PostType postType, String userEmail) {
        User user = userChk(userEmail);
        return bookmarkRepository.findUserBookmarkQuery(postId, postType.toString(), user.getId());
    }

    // 게시글 북마크 삭제
    public void delBookmark(Long postId, String postType, String userEmail) {
        User user = userChk(userEmail);
        bookmarkRepository.deleteByUser_UserIdAndPost_PostIdAndPostType(postId, postType, user.getId());
    }

    // 좋아요 북마크 반환값
    public HashMap<String,String> toggleResponseHashMap(Boolean result, int cnt, Long postId, String postType){
        HashMap<String,String> hs = new HashMap<>();

        hs.put("result",String.valueOf(result));
        hs.put("postType", postType);
        hs.put("postId", String.valueOf(postId));
        hs.put("cnt", String.valueOf(cnt));
        return hs;
    }

    // 좋아요 개수 count
    public int getLikesCnt(Long postId, PostType postType) {
        return likesRepository.findByPostIdAndPostType(postId, postType).size();
    }

    // 북마크 개수 count
    public int getBookmarkCnt(Long postId, PostType postType) {
        return bookmarkRepository.findByPostIdAndPostType(postId, postType).size();
    }

    public User userChk(String userEmail) {
        Optional<User> findByUserEmail = userRepository.findByUserEmail(userEmail);
        if (findByUserEmail.isEmpty()) {
            throw new CustomException(ErrorCode.ILLEGAL_USER_NOT_EXIST);
        }
        return findByUserEmail.get();
    }

}
