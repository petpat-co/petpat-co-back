package com.smile.petpat.post.qna.repository;

import com.smile.petpat.post.qna.domain.Qna;
import com.smile.petpat.post.qna.repository.querydsl.QnaRepositoryQueryDsl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna,Long>, QnaRepositoryQueryDsl {

}
