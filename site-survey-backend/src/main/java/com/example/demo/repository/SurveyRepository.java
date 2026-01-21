package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
