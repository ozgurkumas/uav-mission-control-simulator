package com.ozgurkumas.uav.repository;

import com.ozgurkumas.uav.domain.Mission;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Stores Mission entities in application memory.
 */
public final class InMemoryMissionRepository
        implements MissionRepository {

    private final Map<UUID, Mission> missions;

    public InMemoryMissionRepository() {
        this.missions = new LinkedHashMap<>();
    }

    @Override
    public void save(Mission mission) {
        Objects.requireNonNull(
                mission,
                "Mission cannot be null."
        );

        missions.put(mission.getId(), mission);
    }

    @Override
    public Optional<Mission> findById(UUID id) {
        Objects.requireNonNull(
                id,
                "Mission id cannot be null."
        );

        return Optional.ofNullable(missions.get(id));
    }

    @Override
    public List<Mission> findAll() {
        return List.copyOf(missions.values());
    }

    @Override
    public boolean existsById(UUID id) {
        Objects.requireNonNull(
                id,
                "Mission id cannot be null."
        );

        return missions.containsKey(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        Objects.requireNonNull(
                name,
                "Mission name cannot be null."
        );

        String normalizedName = name.trim();

        return missions.values()
                .stream()
                .anyMatch(mission ->
                        mission.getName()
                                .equalsIgnoreCase(normalizedName)
                );
    }

    @Override
    public boolean deleteById(UUID id) {
        Objects.requireNonNull(
                id,
                "Mission id cannot be null."
        );

        return missions.remove(id) != null;
    }
}