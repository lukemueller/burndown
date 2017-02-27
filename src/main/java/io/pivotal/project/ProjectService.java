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
        ProjectEntity savedProjectEntity = projectRepository.save(new ProjectEntity(
            project.getName(),
            project.getStartDate(),
            project.getHourlyRate()
        ));

        return new Project(
            savedProjectEntity.getId(),
            savedProjectEntity.getName(),
            savedProjectEntity.getHourlyRate(),
            savedProjectEntity.getStartDate(),
            new ArrayList<>()
        );
    }
}
