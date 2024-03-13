package com.smile.petpat.post.common.likes.service;

import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.post.common.likes.domain.Likes;
import com.smile.petpat.post.common.likes.repository.LikesRepository;
import com.smile.petpat.post.rehoming.service.RehomingReaderImpl;
import com.smile.petpat.post.trade.service.TradeReaderImpl;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

import static com.smile.petpat.post.category.domain.PostType.REHOMING;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final CommonUtils commonUtils;
    private final RehomingReaderImpl rehomingReader;
    private final TradeReaderImpl tradeReader;

    @Transactional
    @Override
    public HashMap<String, String> likePost(Long postId, String postType, String userEmail) {

        // 1. 존재하지 않는 postType 의 postId 조회 요청 시 에러 반환
        if (PostType.valueOf(postType) == REHOMING)
            rehomingReader.readRehomingById(postId);
        else {
            tradeReader.readTradeById(postId);
        }
        // 2. 만약 유저가 해당 글을 좋아요 했었다면 -> 삭제
        if (commonUtils.getLikePost(postId, PostType.valueOf(postType), userEmail) != null) {
            commonUtils.delLikes(postId, postType, userEmail);
            int cnt = likesRepository.findByPostIdAndPostType(postId, PostType.valueOf(postType)).size();
            return commonUtils.toggleResponseHashMap(false, cnt, postId, postType);
        } else {
            // 3. 하지 않았다면 -> 저장
            User user = commonUtils.userChk(userEmail);
            likesRepository.save(new Likes(PostType.valueOf(postType), postId, user));
            int cnt = likesRepository.findByPostIdAndPostType(postId, PostType.valueOf(postType)).size();
            return commonUtils.toggleResponseHashMap(true, cnt, postId, postType);
        }

    }

}
