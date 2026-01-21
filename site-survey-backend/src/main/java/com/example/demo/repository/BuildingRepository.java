package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findByPropertyId(Long propertyId);
}
