package com.haotran.theintlligentinventory.utils;

import java.time.LocalDateTime;

public class VehiclePolicy {
    private static final int AGING_DAYS = 90;

    public static boolean isAging(LocalDateTime arrivalDate) {
        return arrivalDate.isBefore(LocalDateTime.now().minusDays(AGING_DAYS));
    }

    public static LocalDateTime getAgingDate() {
        return LocalDateTime.now().minusDays(AGING_DAYS);
    }
}
