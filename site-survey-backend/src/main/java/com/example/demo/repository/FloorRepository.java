package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Floor;
import java.util.List;
public interface FloorRepository extends JpaRepository<Floor, Long> {
	List<Floor> findByBuildingId(Long buildingId);

}
