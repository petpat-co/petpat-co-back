package com.smile.petpat.post.qna.domain;


import javax.persistence.*;

import com.smile.petpat.config.comm.Timestamped;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.user.domain.User;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "TB_QNA")
@Builder
@AllArgsConstructor
public class Qna extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QNA_ID")
    private Long qnaId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_ID",name = "USER_ID")
    private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "POST_TYPE" , nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(name = "VIEW_CNT")
    private int viewCnt;

    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Qna() {
    }

    public Qna(Long qnaId, User user, String title, String content, PostType postType, int viewCnt) {
        this.qnaId = qnaId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.viewCnt = viewCnt;

    }
    @Builder
    public Qna(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(Qna qna){
        this.title = qna.getTitle();
        this.content = qna.getContent();

    }

    public void updateViewCnt(Qna qna) {
        this.viewCnt = qna.getViewCnt() + 1;
    }
}
