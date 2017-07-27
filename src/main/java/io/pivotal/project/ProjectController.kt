package io.pivotal.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
internal class ProjectController @Autowired
constructor(private val projectService: ProjectService) {

    @GetMapping("/projects")
    fun listProjects(): List<Project> {
        return projectService.allProjects()
    }

    @GetMapping("projects/{projectId}")
    fun getProject(@PathVariable projectId: Int): ResponseEntity<ProjectApiRequestResponseWrapper> {
        val project : Project = projectService.getProjectById(projectId) ?:
                return ResponseEntity.notFound().build<ProjectApiRequestResponseWrapper>()

        val matchedProjectApiResponse = ProjectApiRequestResponseWrapper(project)
        return ResponseEntity(matchedProjectApiResponse, HttpStatus.OK)
    }

    @PostMapping("/projects")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createProject(@RequestBody projectApiRequestResponseWrapper: ProjectApiRequestResponseWrapper): ResponseEntity<ProjectApiRequestResponseWrapper> {
        if (projectApiRequestResponseWrapper.project != null) {
            val savedProject = projectService.saveProject(projectApiRequestResponseWrapper.project)

            val createProjectApiResponse = ProjectApiRequestResponseWrapper(savedProject)
            return ResponseEntity(createProjectApiResponse, HttpStatus.CREATED)
        } else {
            throw RuntimeException("handle this case for realz. The kotlin compiler forced our hand here. Rightly so.")
        }
    }
}
