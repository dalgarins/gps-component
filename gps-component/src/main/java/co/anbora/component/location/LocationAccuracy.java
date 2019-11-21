package co.anbora.component.location;

import com.google.android.gms.location.LocationRequest;

public enum LocationAccuracy {

    HIGH(LocationRequest.PRIORITY_HIGH_ACCURACY),
    MEDIUM(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY),
    LOW(LocationRequest.PRIORITY_LOW_POWER);

    private int priority;

    LocationAccuracy(int priorityLowPower) {
        this.priority = priorityLowPower;
    }

    public int getAccuracy() {
        return this.priority;
    }
}
