package io.pivotal.project;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private LocalDate startDate;
    private int budget; // TODO - should expand to List<SOW> domain objects
    private List<Float> burndown;

    public Project(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.hourlyRate = projectEntity.getHourlyRate();
        this.startDate = projectEntity.getStartDate();
        this.budget = projectEntity.getBudget();
        this.burndown = new ArrayList<>();
    }
}
