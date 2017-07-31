package io.pivotal.project

import io.pivotal.project.burndown.BurndownParser
import io.pivotal.project.testhelpers.aProject
import io.pivotal.project.testhelpers.aProjectEntity
import org.junit.Test
import org.mockito.ArgumentCaptor

import java.time.LocalDate
import java.util.*

import org.assertj.core.api.Assertions.assertThat
import org.mockito.Matchers.any
import org.mockito.Matchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.*

class ProjectServiceTest {

    private val mockProjectRepository = mock<ProjectRepository>(ProjectRepository::class.java)
    private val mockBurndownParser = Mockito.mock<BurndownParser>(BurndownParser::class.java)
    private val projectService: ProjectService = ProjectService(mockProjectRepository, mockBurndownParser)

    @Test
    fun getAllProjects_setsProjectFieldsFromRepositoryEntities() {
        val firstProjectEntity = ProjectEntity(123, "project-1", LocalDate.MAX, 500, 10000)
        val secondProjectEntity = ProjectEntity(456, "project-2", LocalDate.MIN, 250, 75000)
        val projectEntities = Arrays.asList(firstProjectEntity, secondProjectEntity)
        `when`(mockProjectRepository.allProjectEntities()).thenReturn(projectEntities)

        val allProjects = projectService.allProjects()

        assertThat(allProjects).hasSize(2)
        val firstProject = allProjects[0]
        assertThat(firstProject.id).isEqualTo(123)
        assertThat(firstProject.name).isEqualTo("project-1")
        assertThat(firstProject.hourlyRate).isEqualTo(500)
        assertThat(firstProject.startDate).isEqualTo(LocalDate.MAX)
        assertThat(firstProject.budget).isEqualTo(10000)

        val secondProject = allProjects[1]
        assertThat(secondProject.id).isEqualTo(456)
        assertThat(secondProject.name).isEqualTo("project-2")
        assertThat(secondProject.hourlyRate).isEqualTo(250)
        assertThat(secondProject.startDate).isEqualTo(LocalDate.MIN)
        assertThat(secondProject.budget).isEqualTo(75000)
    }

    @Test
    fun getProjectById_setsProjectFieldsFromRepositoryEntity() {
        val stubbedLocalDate = LocalDate.of(2017, 2, 13)
        val stubbedHourlyRate = 500
        val stubbedProjectEntity = ProjectEntity(123, "foo-bar", stubbedLocalDate, stubbedHourlyRate, 50000)
        `when`(mockProjectRepository.getProjectEntityById(56)).thenReturn(stubbedProjectEntity)

        val project = projectService.getProjectById(56)

        assertThat(project!!.id).isEqualTo(123)
        assertThat(project.name).isEqualTo("foo-bar")
        assertThat(project.startDate).isEqualTo(stubbedLocalDate)
        assertThat(project.hourlyRate).isEqualTo(stubbedHourlyRate)
        assertThat(project.budget).isEqualTo(50000)
    }

    @Test
    fun getProjectById_setsBurndownListFromCsvParser() {
        val stubbedProjectEntity = aProjectEntity()
        `when`(mockProjectRepository.getProjectEntityById(100)).thenReturn(stubbedProjectEntity)

        val stubbedBurndown = ArrayList<Float>()
        `when`(mockBurndownParser.getBurndownForProjectEntity(stubbedProjectEntity)).thenReturn(stubbedBurndown)

        val project = projectService.getProjectById(100)

        assertThat(project!!.burndown).isSameAs(stubbedBurndown)
    }

    @Test
    fun getProjectById_defaultsBurndownToAnEmptyList() {
        `when`(mockProjectRepository.getProjectEntityById(anyInt())).thenReturn(aProjectEntity())
        `when`(mockBurndownParser.getBurndownForProjectEntity(any<ProjectEntity>(ProjectEntity::class.java))).thenReturn(null)

        val project = projectService.getProjectById(Random().nextInt())

        assertThat(project!!.burndown).isEmpty()
    }

    @Test
    fun getProjectById_returnsEmptyOptionalWhenRepositoryReturnsNull() {
        `when`(mockProjectRepository.getProjectEntityById(anyInt())).thenReturn(null)

        val projectById = projectService.getProjectById(Random().nextInt())

        assertThat(projectById).isNull()
    }

    @Test
    fun saveProject_delegatesToProjectRepository() {
        `when`(mockProjectRepository.save(any<ProjectEntity>())).thenReturn(aProjectEntity())

        val project = Project(
                id = null,
                name = "project-name",
                hourlyRate = 150,
                startDate = LocalDate.MAX,
                budget = 75000
        )
        projectService.saveProject(project)

        val captor = ArgumentCaptor.forClass<ProjectEntity>(ProjectEntity::class.java)
        verify<ProjectRepository>(mockProjectRepository).save(captor.capture())
        val value = captor.value
        assertThat(value.name).isEqualTo("project-name")
        assertThat(value.hourlyRate).isEqualTo(150)
        assertThat(value.startDate).isEqualTo(LocalDate.MAX)
        assertThat(value.budget).isEqualTo(75000)
    }

    @Test
    fun saveProject_returnsNewlyCreatedProject() {
        val stubbedProject = ProjectEntity(10, "baz", LocalDate.MIN, 75, 10000)
        `when`(mockProjectRepository.save(any<ProjectEntity>())).thenReturn(stubbedProject)

        val createdProject = projectService.saveProject(aProject())

        assertThat(createdProject.id).isEqualTo(10)
        assertThat(createdProject.name).isEqualTo("baz")
        assertThat(createdProject.hourlyRate).isEqualTo(75)
        assertThat(createdProject.startDate).isEqualTo(LocalDate.MIN)
        assertThat(createdProject.budget).isEqualTo(10000)
        assertThat(createdProject.burndown).isEmpty()
    }


}