package com.ozgurkumas.uav.domain;

/**
 * Represents the current lifecycle status of a mission.
 */
public enum MissionStatus {
    PLANNED,
    ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}