package com.ozgurkumas.uav.repository;

import com.ozgurkumas.uav.domain.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines persistence operations for Vehicle entities.
 */
public interface VehicleRepository {

    void save(Vehicle vehicle);

    Optional<Vehicle> findById(UUID id);

    List<Vehicle> findAll();

    boolean existsById(UUID id);

    boolean deleteById(UUID id);
}