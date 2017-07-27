package io.pivotal.project

import io.pivotal.project.testhelpers.aProject
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.anyInt
import org.mockito.Mockito
import org.assertj.core.api.KotlinAssertions.assertThat
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate
import java.util.*

class ProjectControllerTest {

    private val mockProjectService: ProjectService = mock<ProjectService>(ProjectService::class.java)
    private val controller: ProjectController = ProjectController(mockProjectService)
    private var mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    @Throws(Exception::class)
    fun listProjects_requestMapping() {
        `when`(mockProjectService.getProjectById(anyInt())).thenReturn(aProject())

        mockMvc
                .perform(get("/projects"))
                .andExpect(status().isOk)
    }

    @Test
    fun listProjects_returnsListOfProjectsFromProjectService() {
        `when`(mockProjectService.allProjects()).thenReturn(Arrays.asList(aProject(), aProject()))

        val projects = controller.listProjects()

        assertThat(projects).hasSize(2)
    }

    @Test
    @Throws(Exception::class)
    fun getProject_requestMapping() {
        `when`(mockProjectService.getProjectById(anyInt())).thenReturn(aProject())

        mockMvc
                .perform(get("/projects/13"))
                .andExpect(status().isOk)

        mockMvc
                .perform(get("/projects/7"))
                .andExpect(status().isOk)
    }

    @Test
    fun getProject_returnsProjectFromProjectService() {
        val stubbedProject = aProject()
        `when`(mockProjectService.getProjectById(57)).thenReturn(stubbedProject)

        val projectWrapper = controller.getProject(57)

        assertThat("1").isEqualTo("1")
        assertThat(projectWrapper.body.project).isSameAs(stubbedProject)
    }

    @Test
    @Throws(Exception::class)
    fun getProject_delegatesProjectIdDownToProjectService() {
        `when`(mockProjectService.getProjectById(anyInt())).thenReturn(aProject())

        controller.getProject(57)
        Mockito.verify<ProjectService>(mockProjectService).getProjectById(57)

        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        mockMvc.perform(get("/projects/99"))

        Mockito.verify<ProjectService>(mockProjectService).getProjectById(99)
    }

    @Test
    @Throws(Exception::class)
    fun getProject_returns404WhenProjectIsNotFound() {
        `when`(
                mockProjectService.getProjectById(anyInt())
        ).thenReturn(null)

        mockMvc
                .perform(get("/projects/60"))
                .andExpect(status().isNotFound)
    }

    @Test
    @Throws(Exception::class)
    fun createProject_requestMapping() {
        `when`(mockProjectService.getProjectById(anyInt())).thenReturn(aProject())

        mockMvc
                .perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated)
    }

    @Test
    @Throws(Exception::class)
    fun createProject_delegatesProjectToProjectService() {
        mockMvc
                .perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"project\": {\n" +
                                "    \"name\": \"foo\",\n" +
                                "    \"start_date\": \"2016-11-28\",\n" +
                                "    \"hourly_rate\": 105,\n" +
                                "    \"budget\": 100000\n" +
                                "  }\n" +
                                "}"))
                .andExpect(status().isCreated)

        val captor = ArgumentCaptor.forClass<Project>(Project::class.java)
        verify<ProjectService>(mockProjectService).saveProject(captor.capture())
        val value = captor.value
        assertThat(value.name).isEqualTo("foo")
        assertThat(value.startDate).isEqualTo(LocalDate.of(2016, 11, 28))
        assertThat(value.hourlyRate).isEqualTo(105)
        assertThat(value.budget).isEqualTo(100000)
    }

    @Test
    fun createProject_returnsProjectFromProjectService() {
        val stubbedProject = aProject()
        `when`(mockProjectService.saveProject(any<Project>())).thenReturn(stubbedProject)

        val projectWrapper = controller.createProject(ProjectApiRequestResponseWrapper())

        assertThat(projectWrapper.body.project).isSameAs(stubbedProject)
    }

}

