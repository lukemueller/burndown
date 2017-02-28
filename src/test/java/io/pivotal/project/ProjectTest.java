package io.pivotal.project;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


public class ProjectTest {

    @Test
    public void constructor_createsProjectFromProjectEntity() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(Integer.MAX_VALUE);
        projectEntity.setName("foo-bar-baz");
        projectEntity.setStartDate(LocalDate.MAX);
        projectEntity.setHourlyRate(Integer.MIN_VALUE);
        projectEntity.setBudget(Integer.MAX_VALUE / 2);

        Project project = new Project(projectEntity);

        assertThat(project.getId()).isEqualTo(projectEntity.getId());
        assertThat(project.getName()).isEqualTo(projectEntity.getName());
        assertThat(project.getStartDate()).isEqualTo(projectEntity.getStartDate());
        assertThat(project.getHourlyRate()).isEqualTo(projectEntity.getHourlyRate());
        assertThat(project.getBudget()).isEqualTo(projectEntity.getBudget());
        assertThat(project.getBurndown()).isEmpty();
    }
}