package io.pivotal.project.burndown

import au.com.bytecode.opencsv.CSVReader
import io.pivotal.project.ProjectEntity
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.FileReader
import java.io.IOException
import java.time.LocalDate
import java.util.*

interface WeeklySpendCalculator {
    fun getWeeklySpendForProjectEntity(projectEntity: ProjectEntity): Map<LocalDate, Float>
}

@Component open class CsvWeeklySpendCalculator : WeeklySpendCalculator {

    override fun getWeeklySpendForProjectEntity(projectEntity: ProjectEntity): Map<LocalDate, Float> {

        val classPathResource = ClassPathResource(projectEntity.name + ".csv")

        val weeklySpendInHours = HashMap<LocalDate, Float>()
        val reader = CSVReader(classPathResource.file.reader())
        reader.readNext() // ignore header line
        while (true) {
            val nextLine: Array<out String> = reader.readNext() ?: break
            val key = LocalDate.parse(nextLine[0])
            if (key.isBefore(projectEntity.startDate)) {
                continue
            }
            val value = nextLine[5].toFloat()

            val existingValue = (weeklySpendInHours as java.util.Map<LocalDate, Float>).getOrDefault(key, 0f)
            weeklySpendInHours.put(key, existingValue!! + value)
        }

        val weeklySpendInDollars = TreeMap<LocalDate, Float>()
        for (key in weeklySpendInHours.keys) {
            val weeklySpendHours : Float = weeklySpendInHours[key] ?:
                    throw RuntimeException("Weekly spend in hours is null for ${key}. This shouldn't happen. Your CSV must be generated incorrectly.")
            val spendInWeek = weeklySpendHours * projectEntity.hourlyRate
            weeklySpendInDollars.put(key, spendInWeek)
        }

        return weeklySpendInDollars
    }
}
