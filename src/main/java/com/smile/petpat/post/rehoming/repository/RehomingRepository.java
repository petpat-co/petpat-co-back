package com.smile.petpat.post.rehoming.repository;

import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.repository.querydsl.RehomingRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RehomingRepository extends JpaRepository<Rehoming, Long>, RehomingRepositoryQuerydsl {
    List<Rehoming> findAllByCategoryAndType(CategoryGroup category, PetCategory type);
}
