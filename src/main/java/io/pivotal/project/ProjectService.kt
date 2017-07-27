package io.pivotal.project

import io.pivotal.project.burndown.BurndownParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.Optional

@Service
internal open class ProjectService @Autowired
constructor(private val projectRepository: ProjectRepository, private val burndownParser: BurndownParser) {

   fun allProjects() : List<Project> =
                projectRepository
                .allProjectEntities()
                        .toList()
                .map{ Project(it) }

    fun getProjectById(projectId: Int): Project? {
        val projectEntityById = projectRepository.getProjectEntityById(projectId) ?: return null

        val burndown = getBurndown(projectEntityById)

        val project = Project(
                id = projectEntityById.id,
                name = projectEntityById.name,
                hourlyRate = projectEntityById.hourlyRate,
                startDate = projectEntityById.startDate,
                budget = projectEntityById.budget,
                burndown = burndown
                )

        return project
    }

    private fun getBurndown(projectEntityById: ProjectEntity): List<Float> {
        val maybeBurndown = Optional.ofNullable(burndownParser.getBurndownForProjectEntity(projectEntityById))
        return maybeBurndown.orElse(emptyList<Float>())
    }

    fun saveProject(project: Project): Project {
        val projectEntity = ProjectEntity(
                name = project.name,
                startDate = project.startDate,
                hourlyRate = project.hourlyRate,
                budget = project.budget)


        val savedProjectEntity = projectRepository.save(projectEntity)

        return Project(
                id = savedProjectEntity.id,
                name = savedProjectEntity.name,
                hourlyRate = savedProjectEntity.hourlyRate,
                startDate = savedProjectEntity.startDate,
                budget = savedProjectEntity.budget,
                burndown = mutableListOf<Float>())
    }
}
