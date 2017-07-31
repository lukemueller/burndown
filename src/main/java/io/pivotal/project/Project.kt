package io.pivotal.project

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.LocalDate

data class Project(val id: Int?,
                   val name: String,
                   @JsonProperty("hourly_rate")
                   val hourlyRate: Int,
                   @JsonProperty("start_date")
                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                   val startDate: LocalDate,
                   val budget: Int, // TODO - should expand to List<SOW> domain objects
                   val burndown: List<Float> = listOf<Float>()
) {

    constructor(projectEntity: ProjectEntity) : this(
            id = projectEntity.id,
            name = projectEntity.name,
            hourlyRate = projectEntity.hourlyRate,
            startDate = projectEntity.startDate,
            budget = projectEntity.budget,
            burndown = listOf<Float>()//TODO: wish this wasn't mutable... not sure if it needs to be yet
    )

}
