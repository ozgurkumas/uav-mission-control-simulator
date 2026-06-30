package com.ozgurkumas.uav.exception;

import java.util.UUID;

/**
 * Thrown when a vehicle cannot be found by its identifier.
 */
public final class VehicleNotFoundException
        extends RuntimeException {

    public VehicleNotFoundException(UUID vehicleId) {
        super("Vehicle not found with id: " + vehicleId);
    }
}