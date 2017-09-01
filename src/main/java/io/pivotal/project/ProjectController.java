package io.pivotal.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
class ProjectController {

    private ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    List<Project> listProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("projects/{projectId}")
    ResponseEntity<ProjectApiRequestResponseWrapper> getProject(@PathVariable int projectId, @RequestParam(required = false, defaultValue = "0") Integer numberOfEmployees) {
        Optional<Project> maybeProject = projectService.getProjectById(projectId);
        if (!maybeProject.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        projectService.buildProjection(maybeProject.get(), numberOfEmployees);
        ProjectApiRequestResponseWrapper matchedProjectApiResponse = new ProjectApiRequestResponseWrapper(maybeProject.get());
        return new ResponseEntity<>(matchedProjectApiResponse, HttpStatus.OK);
    }

    @PostMapping("/projects")
    @ResponseStatus(value = HttpStatus.CREATED)
    ResponseEntity<ProjectApiRequestResponseWrapper> createProject(@RequestBody ProjectApiRequestResponseWrapper projectApiRequestResponseWrapper) {
        Project savedProject = projectService.saveProject(projectApiRequestResponseWrapper.getProject());

        ProjectApiRequestResponseWrapper createProjectApiResponse = new ProjectApiRequestResponseWrapper(savedProject);
        return new ResponseEntity<>(createProjectApiResponse, HttpStatus.CREATED);
    }
}
