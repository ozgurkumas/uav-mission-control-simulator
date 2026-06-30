package com.ozgurkumas.uav.domain;

import java.util.Objects;

/**
 * Represents an immutable geographical position.
 */
public final class Position {

    private final double latitude;
    private final double longitude;
    private final double altitudeMeters;

    public Position(
            double latitude,
            double longitude,
            double altitudeMeters
    ) {
        validateLatitude(latitude);
        validateLongitude(longitude);
        validateAltitude(altitudeMeters);

        this.latitude = latitude;
        this.longitude = longitude;
        this.altitudeMeters = altitudeMeters;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitudeMeters() {
        return altitudeMeters;
    }

    private static void validateLatitude(double latitude) {
        if (!Double.isFinite(latitude)) {
            throw new IllegalArgumentException(
                    "Latitude must be a finite number."
            );
        }

        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException(
                    "Latitude must be between -90 and 90 degrees."
            );
        }
    }

    private static void validateLongitude(double longitude) {
        if (!Double.isFinite(longitude)) {
            throw new IllegalArgumentException(
                    "Longitude must be a finite number."
            );
        }

        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException(
                    "Longitude must be between -180 and 180 degrees."
            );
        }
    }

    private static void validateAltitude(double altitudeMeters) {
        if (!Double.isFinite(altitudeMeters)) {
            throw new IllegalArgumentException(
                    "Altitude must be a finite number."
            );
        }

        if (altitudeMeters < 0.0) {
            throw new IllegalArgumentException(
                    "Altitude cannot be negative."
            );
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Position other)) {
            return false;
        }

        return Double.compare(latitude, other.latitude) == 0
                && Double.compare(longitude, other.longitude) == 0
                && Double.compare(altitudeMeters, other.altitudeMeters) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, altitudeMeters);
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitudeMeters=" + altitudeMeters +
                '}';
    }
}
