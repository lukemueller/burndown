package io.pivotal.project;

import java.time.LocalDate;

public class ProjectEntity {
    private int id;
    private String name;
    private int hourlyRate;
    private LocalDate startDate;

    public ProjectEntity() {
    }

    public ProjectEntity(int id, String name, LocalDate startDate, int hourlyRate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.hourlyRate = hourlyRate;
    }

    public ProjectEntity(String name, LocalDate startDate, int hourlyRate) {
        this.name = name;
        this.startDate = startDate;
        this.hourlyRate = hourlyRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
