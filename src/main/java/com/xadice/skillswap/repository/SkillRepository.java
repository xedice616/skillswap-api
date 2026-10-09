package com.xadice.skillswap.repository;

import com.xadice.skillswap.model.Skill;
import com.xadice.skillswap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findAllByOwner(User owner);

    List<Skill> findAllByCategoryIgnoreCase(String category);
}