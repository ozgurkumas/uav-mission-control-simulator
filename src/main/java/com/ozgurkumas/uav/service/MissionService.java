package com.ozgurkumas.uav.service;

import com.ozgurkumas.uav.domain.FlightState;
import com.ozgurkumas.uav.domain.Mission;
import com.ozgurkumas.uav.domain.MissionStatus;
import com.ozgurkumas.uav.domain.Vehicle;
import com.ozgurkumas.uav.domain.Waypoint;
import com.ozgurkumas.uav.exception.MissionNotFoundException;
import com.ozgurkumas.uav.exception.VehicleNotFoundException;
import com.ozgurkumas.uav.repository.MissionRepository;
import com.ozgurkumas.uav.repository.VehicleRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Provides application operations for managing missions.
 */
public final class MissionService {

    private final MissionRepository missionRepository;
    private final VehicleRepository vehicleRepository;

    public MissionService(
            MissionRepository missionRepository,
            VehicleRepository vehicleRepository
    ) {
        this.missionRepository = Objects.requireNonNull(
                missionRepository,
                "Mission repository cannot be null."
        );

        this.vehicleRepository = Objects.requireNonNull(
                vehicleRepository,
                "Vehicle repository cannot be null."
        );
    }

    public Mission createMission(String name) {
        Mission mission = new Mission(name);

        if (missionRepository.existsByNameIgnoreCase(
                mission.getName()
        )) {
            throw new IllegalArgumentException(
                    "A mission with the same name already exists."
            );
        }

        missionRepository.save(mission);

        return mission;
    }

    public Mission getMission(UUID missionId) {
        Objects.requireNonNull(
                missionId,
                "Mission id cannot be null."
        );

        return missionRepository.findById(missionId)
                .orElseThrow(() ->
                        new MissionNotFoundException(missionId)
                );
    }

    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    public void addWaypoint(
            UUID missionId,
            Waypoint waypoint
    ) {
        Objects.requireNonNull(
                waypoint,
                "Waypoint cannot be null."
        );

        Mission mission = getMission(missionId);

        mission.addWaypoint(waypoint);

        missionRepository.save(mission);
    }

    public void assignMissionToVehicle(
            UUID missionId,
            UUID vehicleId
    ) {
        Objects.requireNonNull(
                vehicleId,
                "Vehicle id cannot be null."
        );

        Mission mission = getMission(missionId);

        Vehicle vehicle = vehicleRepository
                .findById(vehicleId)
                .orElseThrow(() ->
                        new VehicleNotFoundException(vehicleId)
                );

        if (vehicle.getFlightState() != FlightState.IDLE) {
            throw new IllegalStateException(
                    "Only idle vehicles can be assigned to a mission."
            );
        }

        mission.assignTo(vehicle);

        missionRepository.save(mission);
    }

    public void startMission(UUID missionId) {
        Mission mission = getMission(missionId);

        mission.start();

        missionRepository.save(mission);
    }

    public void completeMission(UUID missionId) {
        Mission mission = getMission(missionId);

        mission.complete();

        missionRepository.save(mission);
    }

    public void cancelMission(UUID missionId) {
        Mission mission = getMission(missionId);

        mission.cancel();

        missionRepository.save(mission);
    }

    public void deleteMission(UUID missionId) {
        Mission mission = getMission(missionId);

        if (mission.getStatus() != MissionStatus.PLANNED
                && mission.getStatus() != MissionStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Only planned or cancelled missions can be deleted."
            );
        }

        boolean deleted =
                missionRepository.deleteById(missionId);

        if (!deleted) {
            throw new MissionNotFoundException(missionId);
        }
    }
}