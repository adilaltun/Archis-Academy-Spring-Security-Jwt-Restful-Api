package com.works.archisproject.repository;

import com.works.archisproject.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    Optional<Application> findByNameEqualsIgnoreCase(String name);
}
