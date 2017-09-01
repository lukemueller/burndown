package io.pivotal.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.pivotal.project.burndown.BurndownEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private String name;
    @JsonProperty("hourly_rate")
    private int hourlyRate;
    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonProperty("projected_end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate projectedEndDate;
    @JsonProperty("number_of_employees")
    private int numberOfEmployees;
    private int budget; // TODO - should expand to List<SOW> domain objects
    private List<BurndownEntity> burndown;

    public Project(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.hourlyRate = projectEntity.getHourlyRate();
        this.startDate = projectEntity.getStartDate();
        this.budget = projectEntity.getBudget();
        this.burndown = new ArrayList<>();
        this.projectedEndDate = LocalDate.now();
    }
}
