package com.ozgurkumas.uav.repository;

import com.ozgurkumas.uav.domain.Mission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines persistence operations for Mission entities.
 */
public interface MissionRepository {

    void save(Mission mission);

    Optional<Mission> findById(UUID id);

    List<Mission> findAll();

    boolean existsById(UUID id);

    boolean existsByNameIgnoreCase(String name);

    boolean deleteById(UUID id);
}