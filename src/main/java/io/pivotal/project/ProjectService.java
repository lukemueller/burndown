package io.pivotal.project;

import io.pivotal.project.burndown.BurndownCsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
class ProjectService {

    private ProjectRepository projectRepository;
    private BurndownCsvParser burndownCsvParser;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, BurndownCsvParser burndownCsvParser) {
        this.projectRepository = projectRepository;
        this.burndownCsvParser = burndownCsvParser;
    }

    List<Project> getAllProjects() {
        return projectRepository
            .getAllProjectEntities()
            .stream()
            .map(Project::new)
            .collect(toList());
    }

    Optional<Project> getProjectById(int projectId) {
        ProjectEntity projectEntityById = projectRepository.getProjectEntityById(projectId);

        if (projectEntityById == null) {
            return Optional.empty();
        }

        List<Float> burndown = getBurndown(projectEntityById);

        Project project = Project.builder()
            .id(projectEntityById.getId())
            .name(projectEntityById.getName())
            .hourlyRate(projectEntityById.getHourlyRate())
            .startDate(projectEntityById.getStartDate())
            .budget(projectEntityById.getBudget())
            .burndown(burndown)
            .build();

        return Optional.of(project);
    }

    private List<Float> getBurndown(ProjectEntity projectEntityById) {
        Optional<List<Float>> maybeBurndown = Optional.ofNullable(burndownCsvParser.getBurndownForProjectEntity(projectEntityById));
        return maybeBurndown.orElse(Collections.emptyList());
    }

    Project saveProject(Project project) {
        ProjectEntity projectEntity = ProjectEntity.builder()
            .name(project.getName())
            .startDate(project.getStartDate())
            .hourlyRate(project.getHourlyRate())
            .budget(project.getBudget())
            .build();

        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);

        return Project.builder()
            .id(savedProjectEntity.getId())
            .name(savedProjectEntity.getName())
            .hourlyRate(savedProjectEntity.getHourlyRate())
            .startDate(savedProjectEntity.getStartDate())
            .budget(savedProjectEntity.getBudget())
            .burndown(Collections.emptyList())
            .build();
    }
}
