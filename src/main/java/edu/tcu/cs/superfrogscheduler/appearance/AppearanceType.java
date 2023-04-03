package edu.tcu.cs.superfrogscheduler.appearance;

public enum AppearanceType {
    TCU(100), NONPROFIT(100), PRIVATE(175);

    private int hourlyRate;


    AppearanceType(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }
}
