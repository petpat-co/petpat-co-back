package com.smile.petpat.post.trade.service;

import com.smile.petpat.image.domain.Image;
import com.smile.petpat.image.repository.ImageRepository;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.category.repository.TradeCategoryDetailRepository;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.domain.TradeCommand;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.post.trade.repository.TradeRepository;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class TradeServiceImplTest {

    @Autowired
    private TradeServiceImpl tradeService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TradeCategoryDetailRepository tradeCategoryDetailRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private ImageRepository imageRepository;

    User mockUser;

    List<MultipartFile> initImageList;

    List<MultipartFile> updateImageList;

    // 생성자에서 초기화 작업 수행

    @BeforeEach
    void beforeEach() throws IOException {
        mockUser = User.builder()
                .userEmail("test0001@email.com")
                .nickname("닉네임테스트1")
                .password("passwordtest1234@")
                .profileImgPath("http://testUserProfile1.jpg")
                .build();
       //  BDDMockito.given(userRepository.save(mockUser)).willReturn(mockUser);
        userRepository.save(mockUser);
        initImageList   = getMultipartFiles("logo-1.png","logo-2.png");
        updateImageList = getMultipartFiles("logo-3.png","logo-4.png");


    }

    @AfterEach
    void tearDown() {
        imageRepository.deleteAllInBatch();
        tradeRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();


    }

    @DisplayName("중고거래 게시물을 등록한다.")
    @Test
    void createTrade(){

        // given
        TradeCommand tradeCommand = initTradeCommand(1L,initImageList);
        // when
        Long tradeId = tradeService.registerTrade(tradeCommand, mockUser);
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 아이디입니다.")
        );
        List<Image> imageList = imageRepository.findAllByPostIdAndPostTypeOrderByPostId(tradeId, PostType.TRADE);

        // then
        assertThat(trade.getTradeId()).isNotNull();
        assertThat(trade.getTitle()).isEqualTo("제목테스트");
        assertThat(trade.getContent()).isEqualTo("내용 테스트");
        assertThat(imageList.stream().map(Image::getOriginalFileName).collect(Collectors.toList())).hasSize(2)
                .containsExactlyInAnyOrder("logo-1.png","logo-2.png");

    }
    @DisplayName("중고거래 게시물을 수정한다.")
    @Test
    void updateTrade()  {
        // given
        TradeCommand tradeCommand = initTradeCommand(1L,initImageList);
        Long tradeId = tradeService.registerTrade(tradeCommand, mockUser);

        // when
        tradeService.updateTrade(mockUser,tradeId,initUpdateTradeCommand(2L,updateImageList));
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 아이디입니다.")
        );
        List<Image> imageList = imageRepository.findAllByPostIdAndPostTypeOrderByPostId(tradeId, PostType.TRADE);
        // then
        assertThat(trade.getTradeId()).isEqualTo(1L);
        assertThat(trade.getTitle()).isEqualTo("제목수정테스트");
        assertThat(trade.getContent()).isEqualTo("내용수정테스트");
        assertThat(trade.getTradeCategoryDetail().getTradeCategoryDetailName()).isEqualTo("소프트 사료");
        assertThat(imageList.stream().map(Image::getOriginalFileName).collect(Collectors.toList())).hasSize(2)
                .containsExactlyInAnyOrder("logo-3.png","logo-4.png");

    }

    @DisplayName("중고거래 게시물 삭제한다.")
    @Test
    void deleteTrade(){
        // given
        TradeCommand tradeCommand = initTradeCommand(1L,initImageList);
        Long tradeId = tradeService.registerTrade(tradeCommand, mockUser);
        Long tradeCnt = tradeRepository.count();
        Long imageCnt = imageRepository.count();

        // when
        tradeService.deleteTrade(tradeId,mockUser);
        Long afterTradeCnt =  tradeRepository.count();
        Long currentImgCnt = imageRepository.count();


        // then
        assertThat(tradeCnt).isEqualTo(afterTradeCnt+1);
        assertThat(imageCnt).isEqualTo(currentImgCnt+2);
    }

//
//    @DisplayName("로그인한 유저가 보는 중고거래 목록 화면조회입니다.")
//    @Test
//    void selectTradeListForLogin() {
//
//        // given
//        TradeCommand tradeCommand = initTradeCommand(1L,initImageList);
//
//        Long tradeId = tradeService.registerTrade(tradeCommand, mockUser);
//    }
//
//    @DisplayName("중고거래 게시물 상세조회 하기")
//    @Test
//    void selectTradeDetail()  {
//        // given
//        TradeCommand tradeCommand = initTradeCommand(1L,initImageList);
//
//        // when
//        Long tradeId = tradeService.registerTrade(tradeCommand, mockUser);
//        TradeInfo.TradeDetail tradeDetail = tradeService.tradeDetail(tradeId);
//
//        // then
//        assertThat(tradeDetail.getTitle()).isEqualTo("제목테스트");
//        assertThat(tradeDetail.getContent()).isEqualTo("내용 테스트");
//        assertThat(tradeDetail.getImageList()).hasSize(2)
//                .containsExactlyInAnyOrder("logo-1.png","logo-2.png");
//
//    }

    // 파일 찾기
    private List<MultipartFile> getMultipartFiles(String filename1, String filename2) throws IOException {
        return List.of(
                new MockMultipartFile("image", filename1, "image/png",
                        new FileInputStream(decodePath(getClass().getResource("/images/"+filename1).getFile()))),
                new MockMultipartFile("image2",filename2, "image/png",
                        new FileInputStream(decodePath(getClass().getResource("/images/"+filename2).getFile())))
        );
    }

    // URL 디코딩 수행
    private static String decodePath(String encodedPath) {
        return URLDecoder.decode(encodedPath, StandardCharsets.UTF_8);
    }

    private TradeCommand initUpdateTradeCommand(Long tradeCategoryDetailId,List<MultipartFile> multipartFileList){

        return TradeCommand.builder()
                .user(mockUser)
                .title("제목수정테스트")
                .content("내용수정테스트")
                .price(11000L)
                .cityName("수원시")
                .cityCountryName("수원구")
                .townShipName("수지구")
                .detailAdName("수원로 230")
                .fullAdName("수원시 수원구 수지구 수원로 230")
                .tradeCategoryDetailId(tradeCategoryDetailId)
                .images(multipartFileList)
                .build();
    }
    private TradeCommand initTradeCommand(Long tradeCategoryDetailId,List<MultipartFile> multipartFileList){

        return TradeCommand.builder()
                .user(mockUser)
                .title("제목테스트")
                .content("내용 테스트")
                .price(10000L)
                .cityName("서울시")
                .cityCountryName("양천구")
                .townShipName("목동")
                .detailAdName("목동로 230")
                .fullAdName("서울시 양천구 목동로 230")
                .tradeCategoryDetailId(tradeCategoryDetailId)
                .images(multipartFileList)
                .build();
    }

    private TradeCategoryDetail getTradeCategoryDetail(Long categoryId) {
        return tradeCategoryDetailRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 카테고리디테일아이디 입니다.")
        );
    }
}