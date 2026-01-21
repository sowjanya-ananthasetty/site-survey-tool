package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Floor;
import com.example.demo.entity.Space;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.SpaceRepository;

@Service
public class SpaceImportService {

    private final SpaceRepository spaceRepository;
    private final FloorRepository floorRepository;

    public SpaceImportService(SpaceRepository spaceRepository,
                              FloorRepository floorRepository) {
        this.spaceRepository = spaceRepository;
        this.floorRepository = floorRepository;
    }

    public void importSpaces(MultipartFile file) throws Exception {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(file.getInputStream()));

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);

        for (CSVRecord record : records) {

            String spaceName = record.get("spaceName");
            String spaceType = record.get("spaceType");
            Long floorId = Long.parseLong(record.get("floorId"));

            Floor floor = floorRepository.findById(floorId)
                    .orElseThrow(() ->
                            new RuntimeException("Floor not found: " + floorId));

            Space space = new Space();
            space.setSpaceName(spaceName);
            space.setSpaceType(spaceType);
            space.setFloor(floor);

            spaceRepository.save(space);
        }
    }
}
