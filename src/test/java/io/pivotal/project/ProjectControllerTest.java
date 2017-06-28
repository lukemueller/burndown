package io.pivotal.project;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest {

    private ProjectController controller;
    private ProjectService mockProjectService;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockProjectService = mock(ProjectService.class);

        controller = new ProjectController(mockProjectService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listProjects_requestMapping() throws Exception {
        when(mockProjectService.getProjectById(anyInt())).thenReturn(Optional.of(new Project()));

        mockMvc
            .perform(get("/projects"))
            .andExpect(status().isOk());
    }

    @Test
    public void listProjects_returnsListOfProjectsFromProjectService() {
        when(mockProjectService.getAllProjects()).thenReturn(Arrays.asList(new Project(), new Project()));

        List<Project> projects = controller.listProjects();

        assertThat(projects).hasSize(2);
    }

    @Test
    public void getProject_requestMapping() throws Exception {
        when(mockProjectService.getProjectById(anyInt())).thenReturn(Optional.of(new Project()));

        mockMvc
            .perform(get("/projects/13"))
            .andExpect(status().isOk());

        mockMvc
            .perform(get("/projects/7"))
            .andExpect(status().isOk());
    }

    @Test
    public void getProject_returnsProjectFromProjectService() {
        Project stubbedProject = new Project();
        when(mockProjectService.getProjectById(57)).thenReturn(Optional.of(stubbedProject));

        ResponseEntity<ProjectApiRequestResponseWrapper> projectWrapper = controller.getProject(57);

        assertThat(projectWrapper.getBody().getProject()).isSameAs(stubbedProject);
    }

    @Test
    public void getProject_delegatesProjectIdDownToProjectService() throws Exception {
        when(mockProjectService.getProjectById(anyInt())).thenReturn(Optional.of(new Project()));

        controller.getProject(57);
        Mockito.verify(mockProjectService).getProjectById(57);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/projects/99"));

        Mockito.verify(mockProjectService).getProjectById(99);
    }

    @Test
    public void getProject_returns404WhenProjectIsNotFound() throws Exception {
        when(
            mockProjectService.getProjectById(anyInt())
        ).thenReturn(Optional.empty());

        mockMvc
            .perform(get("/projects/60"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void createProject_requestMapping() throws Exception {
        when(mockProjectService.getProjectById(anyInt())).thenReturn(Optional.of(new Project()));

        mockMvc
            .perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isCreated());
    }

    @Test
    public void createProject_delegatesProjectToProjectService() throws Exception {
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
            .andExpect(status().isCreated());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(mockProjectService).saveProject(captor.capture());
        Project value = captor.getValue();
        assertThat(value.getName()).isEqualTo("foo");
        assertThat(value.getStartDate()).isEqualTo(LocalDate.of(2016, 11, 28));
        assertThat(value.getHourlyRate()).isEqualTo(105);
        assertThat(value.getBudget()).isEqualTo(100000);
    }

    @Test
    public void createProject_returnsProjectFromProjectService() {
        Project stubbedProject = new Project();
        when(mockProjectService.saveProject(any())).thenReturn(stubbedProject);

        ResponseEntity<ProjectApiRequestResponseWrapper> projectWrapper = controller.createProject(new ProjectApiRequestResponseWrapper());

        assertThat(projectWrapper.getBody().getProject()).isSameAs(stubbedProject);
    }
}
