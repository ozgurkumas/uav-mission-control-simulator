package com.ozgurkumas.uav.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a UAV managed by the mission control system.
 */
public final class Vehicle {

    private final UUID id;
    private final String name;

    private Position currentPosition;
    private FlightState flightState;

    public Vehicle(String name, Position initialPosition) {
        this.id = UUID.randomUUID();
        this.name = validateName(name);
        this.currentPosition = Objects.requireNonNull(
                initialPosition,
                "Initial position cannot be null."
        );
        this.flightState = FlightState.IDLE;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public FlightState getFlightState() {
        return flightState;
    }

    public void updatePosition(Position newPosition) {
        this.currentPosition = Objects.requireNonNull(
                newPosition,
                "New position cannot be null."
        );
    }

    public void transitionTo(FlightState newState) {
        Objects.requireNonNull(
                newState,
                "Flight state cannot be null."
        );

        if (!isValidTransition(flightState, newState)) {
            throw new IllegalStateException(
                    "Invalid flight state transition: "
                            + flightState
                            + " -> "
                            + newState
            );
        }

        this.flightState = newState;
    }

    private boolean isValidTransition(
            FlightState currentState,
            FlightState newState
    ) {
        return switch (currentState) {
            case IDLE ->
                    newState == FlightState.ARMED;

            case ARMED ->
                    newState == FlightState.TAKING_OFF
                            || newState == FlightState.IDLE;

            case TAKING_OFF ->
                    newState == FlightState.AIRBORNE;

            case AIRBORNE ->
                    newState == FlightState.RETURNING_HOME
                            || newState == FlightState.LANDING;

            case RETURNING_HOME ->
                    newState == FlightState.LANDING;

            case LANDING ->
                    newState == FlightState.LANDED;

            case LANDED ->
                    newState == FlightState.IDLE;
        };
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Vehicle name cannot be null or blank."
            );
        }

        return name.trim();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Vehicle other)) {
            return false;
        }

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentPosition=" + currentPosition +
                ", flightState=" + flightState +
                '}';
    }
}