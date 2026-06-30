package com.ozgurkumas.uav.service;

import com.ozgurkumas.uav.domain.FlightState;
import com.ozgurkumas.uav.domain.Position;
import com.ozgurkumas.uav.domain.Vehicle;
import com.ozgurkumas.uav.exception.VehicleNotFoundException;
import com.ozgurkumas.uav.repository.VehicleRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Provides application operations for managing vehicles.
 */
public final class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(
            VehicleRepository vehicleRepository
    ) {
        this.vehicleRepository = Objects.requireNonNull(
                vehicleRepository,
                "Vehicle repository cannot be null."
        );
    }

    public Vehicle registerVehicle(
            String name,
            Position initialPosition
    ) {
        Vehicle vehicle = new Vehicle(
                name,
                initialPosition
        );

        if (vehicleRepository.existsByNameIgnoreCase(
                vehicle.getName()
        )) {
            throw new IllegalArgumentException(
                    "A vehicle with the same name already exists."
            );
        }

        vehicleRepository.save(vehicle);

        return vehicle;
    }

    public Vehicle getVehicle(UUID vehicleId) {
        Objects.requireNonNull(
                vehicleId,
                "Vehicle id cannot be null."
        );

        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(vehicleId)
                );
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void updatePosition(
            UUID vehicleId,
            Position newPosition
    ) {
        Objects.requireNonNull(
                newPosition,
                "New position cannot be null."
        );

        Vehicle vehicle = getVehicle(vehicleId);

        vehicle.updatePosition(newPosition);

        vehicleRepository.save(vehicle);
    }

    public void transitionVehicle(
            UUID vehicleId,
            FlightState newState
    ) {
        Objects.requireNonNull(
                newState,
                "Flight state cannot be null."
        );

        Vehicle vehicle = getVehicle(vehicleId);

        vehicle.transitionTo(newState);

        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(UUID vehicleId) {
        Objects.requireNonNull(
                vehicleId,
                "Vehicle id cannot be null."
        );

        boolean deleted =
                vehicleRepository.deleteById(vehicleId);

        if (!deleted) {
            throw new VehicleNotFoundException(vehicleId);
        }
    }
}