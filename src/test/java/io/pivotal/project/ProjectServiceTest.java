package io.pivotal.project;

import io.pivotal.project.burndown.BurndownCsvParser;
import io.pivotal.project.burndown.BurndownPeriod;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    private ProjectRepository mockProjectRepository;
    private BurndownCsvParser mockBurndownCsvParser;
    private ProjectService projectService;

    @Before
    public void setup() {
        mockProjectRepository = mock(ProjectRepository.class);
        mockBurndownCsvParser = mock(BurndownCsvParser.class);

        projectService = new ProjectService(mockProjectRepository, mockBurndownCsvParser);
    }

    @Test
    public void getAllProjects_setsProjectFieldsFromRepositoryEntities() {
        ProjectEntity firstProjectEntity = new ProjectEntity(123, "project-1", LocalDate.MAX, 500, 10000);
        ProjectEntity secondProjectEntity = new ProjectEntity(456, "project-2", LocalDate.MIN, 250, 75000);
        List<ProjectEntity> projectEntities = Arrays.asList(firstProjectEntity, secondProjectEntity);
        when(mockProjectRepository.getAllProjectEntities()).thenReturn(projectEntities);

        List<Project> allProjects = projectService.getAllProjects();

        assertThat(allProjects).hasSize(2);
        Project firstProject = allProjects.get(0);
        assertThat(firstProject.getId()).isEqualTo(123);
        assertThat(firstProject.getName()).isEqualTo("project-1");
        assertThat(firstProject.getHourlyRate()).isEqualTo(500);
        assertThat(firstProject.getStartDate()).isEqualTo(LocalDate.MAX);
        assertThat(firstProject.getBudget()).isEqualTo(10000);

        Project secondProject = allProjects.get(1);
        assertThat(secondProject.getId()).isEqualTo(456);
        assertThat(secondProject.getName()).isEqualTo("project-2");
        assertThat(secondProject.getHourlyRate()).isEqualTo(250);
        assertThat(secondProject.getStartDate()).isEqualTo(LocalDate.MIN);
        assertThat(secondProject.getBudget()).isEqualTo(75000);
    }

    @Test
    public void getProjectById_setsProjectFieldsFromRepositoryEntity() {
        LocalDate stubbedLocalDate = LocalDate.of(2017, 2, 13);
        int stubbedHourlyRate = 500;
        ProjectEntity stubbedProjectEntity = new ProjectEntity(123, "foo-bar", stubbedLocalDate, stubbedHourlyRate, 50000);
        when(mockProjectRepository.getProjectEntityById(56)).thenReturn(stubbedProjectEntity);

        Optional<Project> project = projectService.getProjectById(56);

        Project actualProject = project.get();
        assertThat(actualProject.getId()).isEqualTo(123);
        assertThat(actualProject.getName()).isEqualTo("foo-bar");
        assertThat(actualProject.getStartDate()).isEqualTo(stubbedLocalDate);
        assertThat(actualProject.getHourlyRate()).isEqualTo(stubbedHourlyRate);
        assertThat(actualProject.getBudget()).isEqualTo(50000);
    }

    @Test
    public void getProjectById_setsBurndownListFromCsvParser() {
        ProjectEntity stubbedProjectEntity = new ProjectEntity(2, "some-name-that-matches-openair", null, 0, 0);
        when(mockProjectRepository.getProjectEntityById(100)).thenReturn(stubbedProjectEntity);

        ArrayList<BurndownPeriod> stubbedBurndown = new ArrayList<>();
        when(mockBurndownCsvParser.getBurndownForProjectEntity(stubbedProjectEntity)).thenReturn(stubbedBurndown);

        Optional<Project> project = projectService.getProjectById(100);

        Project actualProject = project.get();
        assertThat(actualProject.getBurndown()).isSameAs(stubbedBurndown);
    }

    @Test
    public void getProjectById_defaultsBurndownToAnEmptyList() {
        when(mockProjectRepository.getProjectEntityById(anyInt())).thenReturn(new ProjectEntity());
        when(mockBurndownCsvParser.getBurndownForProjectEntity(any(ProjectEntity.class))).thenReturn(null);

        Project project = projectService.getProjectById(new Random().nextInt()).get();

        assertThat(project.getBurndown()).isEmpty();
    }

    @Test
    public void getProjectById_returnsEmptyOptionalWhenRepositoryReturnsNull() {
        when(mockProjectRepository.getProjectEntityById(anyInt())).thenReturn(null);

        Optional<Project> projectById = projectService.getProjectById(new Random().nextInt());

        assertThat(projectById.isPresent()).isFalse();
    }

    @Test
    public void saveProject_delegatesToProjectRepository() {
        when(mockProjectRepository.save(any())).thenReturn(new ProjectEntity());

        Project project = new Project();
        project.setName("project-name");
        project.setHourlyRate(150);
        project.setStartDate(LocalDate.MAX);
        project.setBudget(75000);
        projectService.saveProject(project);

        ArgumentCaptor<ProjectEntity> captor = ArgumentCaptor.forClass(ProjectEntity.class);
        verify(mockProjectRepository).save(captor.capture());
        ProjectEntity value = captor.getValue();
        assertThat(value.getName()).isEqualTo("project-name");
        assertThat(value.getHourlyRate()).isEqualTo(150);
        assertThat(value.getStartDate()).isEqualTo(LocalDate.MAX);
        assertThat(value.getBudget()).isEqualTo(75000);
    }

    @Test
    public void saveProject_returnsNewlyCreatedProject() {
        ProjectEntity stubbedProject = new ProjectEntity(10, "baz", LocalDate.MIN, 75, 10000);
        when(mockProjectRepository.save(any())).thenReturn(stubbedProject);

        Project createdProject = projectService.saveProject(new Project());

        assertThat(createdProject.getId()).isEqualTo(10);
        assertThat(createdProject.getName()).isEqualTo("baz");
        assertThat(createdProject.getHourlyRate()).isEqualTo(75);
        assertThat(createdProject.getStartDate()).isEqualTo(LocalDate.MIN);
        assertThat(createdProject.getBudget()).isEqualTo(10000);
        assertThat(createdProject.getBurndown()).isEmpty();
    }

}