package io.pivotal.project

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

import java.time.LocalDate

data class ProjectEntity(
        val id: Int = 0,
        val name: String,
        val startDate: LocalDate,
        val hourlyRate: Int = 0,
        val budget: Int = 0 // TODO - should expand to List<SOW> domain objects
)
