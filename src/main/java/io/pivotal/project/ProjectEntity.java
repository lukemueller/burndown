package io.pivotal.project;

import java.time.LocalDate;

public class ProjectEntity {
    private int id;
    private String name;
    private LocalDate startDate;
    private int hourlyRate;
    private int budget; // TODO - should expand to List<SOW> domain objects

    public ProjectEntity() {
    }

    ProjectEntity(int id, String name, LocalDate startDate, int hourlyRate, int budget) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.startDate = startDate;
        this.budget = budget;
    }

    int getId() {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
