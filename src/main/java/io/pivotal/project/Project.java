package io.pivotal.project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private int id;
    private String name;
    @JsonProperty("hourly_rate")
    private int hourlyRate;
    @JsonProperty("start_date")
    private LocalDate startDate;
    private int budget; // TODO - should expand to List<SOW> domain objects
    private List<Float> burndown;

    public Project() {
    }

    public Project(int id, String name, int hourlyRate, LocalDate startDate, int budget, List<Float> burndown) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.startDate = startDate;
        this.budget = budget;
        this.burndown = burndown;
    }

    public Project(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.hourlyRate = projectEntity.getHourlyRate();
        this.startDate = projectEntity.getStartDate();
        this.budget = projectEntity.getBudget();
        this.burndown = new ArrayList<>();
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

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Float> getBurndown() {
        return burndown;
    }

    public void setBurndown(List<Float> burndown) {
        this.burndown = burndown;
    }
}
