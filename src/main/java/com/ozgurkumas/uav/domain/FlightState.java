package com.ozgurkumas.uav.domain;

/**
 * Represents the current flight state of a UAV.
 */
public enum FlightState {
    IDLE,
    ARMED,
    TAKING_OFF,
    AIRBORNE,
    RETURNING_HOME,
    LANDING,
    LANDED
}