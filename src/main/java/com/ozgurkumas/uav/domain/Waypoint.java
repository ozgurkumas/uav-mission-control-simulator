package com.ozgurkumas.uav.domain;

import java.util.Objects;

/**
 * Represents a named point that can be used in a UAV mission route.
 */
public final class Waypoint {

    private final String name;
    private final Position position;

    public Waypoint(String name, Position position) {
        validateName(name);

        this.name = name;
        this.position = Objects.requireNonNull(
                position,
                "Position cannot be null."
        );
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Waypoint name cannot be null or blank."
            );
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Waypoint other)) {
            return false;
        }

        return name.equals(other.name)
                && position.equals(other.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}