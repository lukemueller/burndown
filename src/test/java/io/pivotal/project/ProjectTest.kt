package io.pivotal.project

import org.junit.Test

import java.time.LocalDate

import org.assertj.core.api.Assertions.assertThat


class ProjectTest {

    //TODO: This dependency is awkward... domain shouldn't depend on persistence
    //TODO: We should probably NOT have this constructor at all...
    @Test
    fun constructor_createsProjectFromProjectEntity() {
        val projectEntity = ProjectEntity(
                id = Integer.MAX_VALUE,
                name = "foo-bar-baz",
                startDate = LocalDate.MAX,
                hourlyRate = Integer.MIN_VALUE,
                budget = Integer.MAX_VALUE / 2
        )

        val project = Project(projectEntity)

        assertThat(project.id).isEqualTo(projectEntity.id)
        assertThat(project.name).isEqualTo(projectEntity.name)
        assertThat(project.startDate).isEqualTo(projectEntity.startDate)
        assertThat(project.hourlyRate).isEqualTo(projectEntity.hourlyRate)
        assertThat(project.budget).isEqualTo(projectEntity.budget)
        assertThat(project.burndown).isEmpty()
    }
}