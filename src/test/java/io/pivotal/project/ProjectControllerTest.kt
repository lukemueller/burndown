package io.pivotal.project

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.project.testhelpers.aProject
import io.pivotal.project.testhelpers.aProjectWith
import org.junit.Test
import com.nhaarman.mockito_kotlin.any
import org.assertj.core.api.KotlinAssertions.assertThat
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate
import java.util.*

class ProjectControllerTest {

    private val mockProjectService: ProjectService = mock()
    private val controller: ProjectController = ProjectController(mockProjectService)
    private var mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    @Throws(Exception::class)
    fun listProjects_requestMapping() {
        whenever(mockProjectService.getProjectById(any())).thenReturn(aProject())

        mockMvc
                .perform(get("/projects"))
                .andExpect(status().isOk)
    }

    @Test
    fun listProjects_returnsListOfProjectsFromProjectService() {
        whenever(mockProjectService.allProjects()).thenReturn(Arrays.asList(aProject(), aProject()))

        val projects = controller.listProjects()

        assertThat(projects).hasSize(2)
    }

    @Test
    @Throws(Exception::class)
    fun getProject_requestMapping() {
        whenever(mockProjectService.getProjectById(any())).thenReturn(aProject())

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
        whenever(mockProjectService.getProjectById(57)).thenReturn(stubbedProject)

        val projectWrapper = controller.getProject(57)

        assertThat("1").isEqualTo("1")
        assertThat(projectWrapper.body.project).isSameAs(stubbedProject)
    }

    @Test
    @Throws(Exception::class)
    fun getProject_delegatesProjectIdDownToProjectService() {
        whenever(mockProjectService.getProjectById(any())).thenReturn(aProject())

        controller.getProject(57)
        verify<ProjectService>(mockProjectService).getProjectById(57)

        val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        mockMvc.perform(get("/projects/99"))

        verify<ProjectService>(mockProjectService).getProjectById(99)
    }

    @Test
    @Throws(Exception::class)
    fun getProject_returns404WhenProjectIsNotFound() {
        whenever(
                mockProjectService.getProjectById(any())
        ).thenReturn(null)

        mockMvc
                .perform(get("/projects/60"))
                .andExpect(status().isNotFound)
    }

    @Test
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

        verify<ProjectService>(mockProjectService).saveProject(Project(
                id = null,
                name = "foo",
                startDate = LocalDate.of(2016, 11, 28),
                hourlyRate = 105,
                budget = 100000
        ))
    }

    @Test
    fun createProject_returnsProjectFromProjectService() {
        val stubbedProject = aProjectWith(
                name = "foo",
                startDate = LocalDate.of(2016, 11, 28),
                hourlyRate = 105,
                budget = 100000
        )
        whenever(mockProjectService.saveProject(any())).thenReturn(stubbedProject)

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
                .andExpect(content().json("{\n" +
                        "  \"project\": {\n" +
                        "    \"name\": \"foo\",\n" +
                        "    \"start_date\": \"2016-11-28\",\n" +
                        "    \"hourly_rate\": 105,\n" +
                        "    \"budget\": 100000\n" +
                        "  }\n" +
                        "}"))


    }

}

