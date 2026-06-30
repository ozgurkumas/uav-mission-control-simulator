package com.ozgurkumas.uav.exception;

import java.util.UUID;

/**
 * Thrown when a mission cannot be found by its identifier.
 */
public final class MissionNotFoundException
        extends RuntimeException {

    public MissionNotFoundException(UUID missionId) {
        super("Mission not found with id: " + missionId);
    }
}