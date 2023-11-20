package com.smile.petpat.post.qna.service;

import com.smile.petpat.post.qna.domain.Qna;
import com.smile.petpat.post.qna.domain.QnaReader;
import com.smile.petpat.post.qna.domain.QnaStore;
import com.smile.petpat.post.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QnaStoreImpl implements QnaStore {
    private final QnaRepository qnaRepository;
    private final QnaReader qnaReader;

    @Override
    public Qna store(Qna initQna) {
        return qnaRepository.save(initQna);
    }

    @Override
    public void delete(Long qnaId, Long userId) {
        qnaReader.userChk(userId, qnaId);
        qnaRepository.deleteById(qnaId);
    }

}
