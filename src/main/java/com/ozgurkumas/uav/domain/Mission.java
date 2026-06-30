package com.ozgurkumas.uav.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a UAV mission containing an ordered list of waypoints.
 */
public final class Mission {

    private final UUID id;
    private final String name;
    private final List<Waypoint> waypoints;

    public Mission(String name) {
        this.id = UUID.randomUUID();
        this.name = validateName(name);
        this.waypoints = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Waypoint> getWaypoints() {
        return List.copyOf(waypoints);
    }

    public void addWaypoint(Waypoint waypoint) {
        Objects.requireNonNull(
                waypoint,
                "Waypoint cannot be null."
        );

        boolean nameAlreadyExists = waypoints.stream()
                .anyMatch(existingWaypoint ->
                        existingWaypoint.getName()
                                .equalsIgnoreCase(waypoint.getName())
                );

        if (nameAlreadyExists) {
            throw new IllegalArgumentException(
                    "A waypoint with the same name already exists."
            );
        }

        waypoints.add(waypoint);
    }

    public int getWaypointCount() {
        return waypoints.size();
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Mission name cannot be null or blank."
            );
        }

        return name.trim();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Mission other)) {
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
        return "Mission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", waypointCount=" + waypoints.size() +
                '}';
    }
}