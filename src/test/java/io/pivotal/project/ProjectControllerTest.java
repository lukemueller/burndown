package io.pivotal.project;

import io.pivotal.project.burndown.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProjectControllerTest {

    private ProjectController controller;
    private ProjectService mockProjectService;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockProjectService = Mockito.mock(ProjectService.class);

        controller = new ProjectController(mockProjectService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void requestMapping() throws Exception {
        Mockito.when(mockProjectService.getBurndownForProjectId(anyInt())).thenReturn(Optional.of(new Project()));

        mockMvc
            .perform(get("/projects/13/burndown"))
            .andExpect(status().isOk());

        mockMvc
            .perform(get("/projects/7/burndown"))
            .andExpect(status().isOk());
    }

    @Test
    public void returnsProjectFromProjectService() {
        Project stubbedProject = new Project();
        Mockito.when(mockProjectService.getBurndownForProjectId(57)).thenReturn(Optional.of(stubbedProject));

        ResponseEntity<Project> projectResponseEntity = controller.getProjectBurndown(57);

        assertThat(projectResponseEntity.getBody()).isSameAs(stubbedProject);
    }

    @Test
    public void passesProjectIdDownToProjectService() throws Exception {
        Mockito.when(mockProjectService.getBurndownForProjectId(anyInt())).thenReturn(Optional.of(new Project()));

        controller.getProjectBurndown(57);
        Mockito.verify(mockProjectService).getBurndownForProjectId(57);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/projects/99/burndown"));

        Mockito.verify(mockProjectService).getBurndownForProjectId(99);
    }

    @Test
    public void returns404WhenProjectIsNotFound() throws Exception {
        Mockito.when(
            mockProjectService.getBurndownForProjectId(anyInt())
        ).thenReturn(Optional.empty());

        mockMvc
            .perform(get("/projects/60/burndown"))
            .andExpect(status().isNotFound());
    }
}
