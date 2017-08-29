package io.pivotal.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {
    private int id;
    private String name;
    private LocalDate startDate;
    private int hourlyRate;
    private int budget; // TODO - should expand to List<SOW> domain objects
}
