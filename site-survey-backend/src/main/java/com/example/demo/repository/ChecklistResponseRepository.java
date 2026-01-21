package com.example.demo.repository;

import com.example.demo.entity.ChecklistResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChecklistResponseRepository
        extends JpaRepository<ChecklistResponse, Long> {

    List<ChecklistResponse> findByTargetTypeAndTargetId(
            String targetType, Long targetId);

    // ✅ Milestone 4 – Summary queries
    long countBySubmittedAtIsNull();        // drafts
    long countBySubmittedAtIsNotNull();     // submitted
}
