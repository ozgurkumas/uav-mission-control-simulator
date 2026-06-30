package com.ozgurkumas.uav.repository;

import com.ozgurkumas.uav.domain.Vehicle;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Stores Vehicle entities in application memory.
 */
public final class InMemoryVehicleRepository
        implements VehicleRepository {

    private final Map<UUID, Vehicle> vehicles;

    public InMemoryVehicleRepository() {
        this.vehicles = new LinkedHashMap<>();
    }

    @Override
    public void save(Vehicle vehicle) {
        Objects.requireNonNull(
                vehicle,
                "Vehicle cannot be null."
        );

        vehicles.put(vehicle.getId(), vehicle);
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        Objects.requireNonNull(
                id,
                "Vehicle id cannot be null."
        );

        return Optional.ofNullable(vehicles.get(id));
    }

    @Override
    public List<Vehicle> findAll() {
        return List.copyOf(vehicles.values());
    }

    @Override
    public boolean existsById(UUID id) {
        Objects.requireNonNull(
                id,
                "Vehicle id cannot be null."
        );

        return vehicles.containsKey(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        Objects.requireNonNull(
                name,
                "Vehicle name cannot be null."
        );

        String normalizedName = name.trim();

        return vehicles.values()
                .stream()
                .anyMatch(vehicle ->
                        vehicle.getName()
                                .equalsIgnoreCase(normalizedName)
                );
    }

    @Override
    public boolean deleteById(UUID id) {
        Objects.requireNonNull(
                id,
                "Vehicle id cannot be null."
        );

        return vehicles.remove(id) != null;
    }
}