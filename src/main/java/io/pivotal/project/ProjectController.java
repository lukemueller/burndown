package io.pivotal.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
class ProjectController {

    private ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("projects/{projectId}")
    ResponseEntity<Project> getProject(@PathVariable int projectId) {
        Optional<Project> project = projectService.getProjectById(projectId);
        if (!project.isPresent()) {
            return new ResponseEntity<>(new Project(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project.get(), HttpStatus.OK);
    }

    @PostMapping("/projects")
    ResponseEntity<Project> createProject(@RequestBody CreateProjectApiRequest createProjectApiRequest) {
        Project savedProject = projectService.saveProject(createProjectApiRequest.getProject());

        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }
}
