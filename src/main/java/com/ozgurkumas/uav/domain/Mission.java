package com.ozgurkumas.uav.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a UAV mission containing an ordered list of waypoints.
 */
public final class Mission {

    private final UUID id;
    private final String name;
    private final List<Waypoint> waypoints;

    private MissionStatus status;
    private UUID assignedVehicleId;

    public Mission(String name) {
        this.id = UUID.randomUUID();
        this.name = validateName(name);
        this.waypoints = new ArrayList<>();
        this.status = MissionStatus.PLANNED;
        this.assignedVehicleId = null;
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

    public MissionStatus getStatus() {
        return status;
    }

    public Optional<UUID> getAssignedVehicleId() {
        return Optional.ofNullable(assignedVehicleId);
    }

    public int getWaypointCount() {
        return waypoints.size();
    }

    public void addWaypoint(Waypoint waypoint) {
        ensureMissionIsEditable();

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

    public void assignTo(Vehicle vehicle) {
        Objects.requireNonNull(
                vehicle,
                "Vehicle cannot be null."
        );

        if (status != MissionStatus.PLANNED) {
            throw new IllegalStateException(
                    "Only planned missions can be assigned."
            );
        }

        this.assignedVehicleId = vehicle.getId();
        this.status = MissionStatus.ASSIGNED;
    }

    public void start() {
        if (status != MissionStatus.ASSIGNED) {
            throw new IllegalStateException(
                    "Only assigned missions can be started."
            );
        }

        if (waypoints.isEmpty()) {
            throw new IllegalStateException(
                    "A mission must contain at least one waypoint before it can start."
            );
        }

        this.status = MissionStatus.IN_PROGRESS;
    }

    public void complete() {
        if (status != MissionStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Only missions in progress can be completed."
            );
        }

        this.status = MissionStatus.COMPLETED;
    }

    public void cancel() {
        if (status == MissionStatus.COMPLETED) {
            throw new IllegalStateException(
                    "A completed mission cannot be cancelled."
            );
        }

        if (status == MissionStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Mission is already cancelled."
            );
        }

        this.status = MissionStatus.CANCELLED;
    }

    private void ensureMissionIsEditable() {
        if (status != MissionStatus.PLANNED) {
            throw new IllegalStateException(
                    "Waypoints can only be modified while the mission is planned."
            );
        }
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
                ", status=" + status +
                ", assignedVehicleId=" + assignedVehicleId +
                '}';
    }
}