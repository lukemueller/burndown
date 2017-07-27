package io.pivotal.project.testhelpers

import io.pivotal.project.Project
import io.pivotal.project.ProjectEntity
import java.time.LocalDate

fun aProject(): Project {
    return Project(id = 1,
            name = "Cool health insurance app",
            hourlyRate = 250,
            budget = 600000,
            burndown = listOf<Float>(),
            startDate = LocalDate.of(2012, 9, 22)
    )
}

fun aProjectEntityWith(id: Int = 1,
                       name: String = "Cool health insurance app",
                       hourlyRate: Int = 250,
                       budget: Int = 600000,
                       startDate: LocalDate = LocalDate.of(2012, 9, 22)
): ProjectEntity {
    return ProjectEntity(id = id,
            name = name,
            hourlyRate = hourlyRate,
            budget = budget,
            startDate = startDate
    )
}

fun aProjectEntity() = ProjectEntity(2, "some-name-that-matches-openair", LocalDate.of(2012, 9, 18), 0, 0)