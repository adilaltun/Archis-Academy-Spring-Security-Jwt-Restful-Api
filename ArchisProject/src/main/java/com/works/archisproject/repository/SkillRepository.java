package com.works.archisproject.repository;

import com.works.archisproject.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill,Long> {

    Optional<Skill> findByNameEqualsIgnoreCase(String name);

}
