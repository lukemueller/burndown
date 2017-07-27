package io.pivotal.project.burndown

import io.pivotal.project.testhelpers.aProjectEntityWith
import org.junit.Test

import java.time.LocalDate

import org.assertj.core.api.Assertions.assertThat

class CsvWeeklySpendCalculatorTest {

    @Test
    fun getWeeklySpendForProjectByName() {
        val projectEntity = aProjectEntityWith(
                name = "test-project-name",
                hourlyRate = 10,
                startDate = LocalDate.of(2015, 10, 26))

        val calculator = CsvWeeklySpendCalculator()
        val weeklySpend = calculator.getWeeklySpendForProjectEntity(projectEntity)

        assertThat(weeklySpend).hasSize(3)
        assertThat(weeklySpend[LocalDate.of(2015, 10, 26)]).isEqualTo(60f)
        assertThat(weeklySpend[LocalDate.of(2015, 11, 18)]).isEqualTo(110f)
        assertThat(weeklySpend[LocalDate.of(2015, 11, 25)]).isEqualTo(60f)
    }
}