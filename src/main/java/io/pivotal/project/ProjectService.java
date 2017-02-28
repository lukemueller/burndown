package io.pivotal.project;

import io.pivotal.project.burndown.BurndownCsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class ProjectService {

    private ProjectRepository projectRepository;
    private BurndownCsvParser burndownCsvParser;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, BurndownCsvParser burndownCsvParser) {
        this.projectRepository = projectRepository;
        this.burndownCsvParser = burndownCsvParser;
    }

    Optional<Project> getProjectById(int projectId) {
        ProjectEntity projectEntityById = projectRepository.getProjectEntityById(projectId);
        List<Float> burndownByProjectName = burndownCsvParser.getBurndownForProjectEntity(projectEntityById);

        return Optional.of(new Project(
            projectEntityById.getId(),
            projectEntityById.getName(),
            projectEntityById.getHourlyRate(),
            projectEntityById.getStartDate(),
            burndownByProjectName == null ? new ArrayList<>() : burndownByProjectName
        ));
    }

    Project saveProject(Project project) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(project.getName());
        projectEntity.setStartDate(project.getStartDate());
        projectEntity.setHourlyRate(project.getHourlyRate());
        projectEntity.setBudget(project.getBudget());

        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);

        return new Project(
            savedProjectEntity.getId(),
            savedProjectEntity.getName(),
            savedProjectEntity.getHourlyRate(),
            savedProjectEntity.getStartDate(),
            new ArrayList<>()
        );
    }
}
