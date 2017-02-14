package io.pivotal.project;

import io.pivotal.project.burndown.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
class ProjectController {

    private ProjectService burndownService;

    ProjectController(ProjectService burndownService) {
        this.burndownService = burndownService;
    }

    @GetMapping("projects/{projectId}/burndown")
    @ResponseBody
    ResponseEntity<Project> getProjectBurndown(@PathVariable int projectId) {
        Optional<Project> project = burndownService.getBurndownForProjectId(projectId);
        if (!project.isPresent()) {
            return new ResponseEntity<>(new Project(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project.get(), HttpStatus.OK);
    }
}
