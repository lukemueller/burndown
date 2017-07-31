package io.pivotal.project.burndown

import io.pivotal.project.ProjectEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDate
import java.util.ArrayList

interface BurndownParser {
    fun getBurndownForProjectEntity(projectEntity: ProjectEntity): List<Float>
}

@Component
class CsvBurndownParser @Autowired
constructor(private val weeklySpendCalculator: WeeklySpendCalculator) {

    fun getBurndownForProjectEntity(projectEntity: ProjectEntity): List<Float> {
        val burndown = ArrayList<Float>()
        val originalBudget = projectEntity.budget.toFloat()
        burndown.add(originalBudget)

        val weeklySpend = weeklySpendCalculator.getWeeklySpendForProjectEntity(projectEntity)
        for (weekStarting in weeklySpend.keys) {
            val previousWeekRemainingBudget = burndown[burndown.size - 1]
            val spendForWeek: Float = weeklySpend[weekStarting] ?: throw RuntimeException("somehow spend for $weekStarting was null")
            burndown.add(previousWeekRemainingBudget - spendForWeek)
        }

        return burndown
    }
}