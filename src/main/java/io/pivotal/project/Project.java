package io.pivotal.project;

import java.time.LocalDate;
import java.util.List;

public class Project {
    private int hourlyRate;
    private LocalDate startDate;
    private List<Float> burndown;

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Float> getBurndown() {
        return burndown;
    }

    public void setBurndown(List<Float> burndown) {
        this.burndown = burndown;
    }
}
