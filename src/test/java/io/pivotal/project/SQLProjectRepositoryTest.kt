package io.pivotal.project

import org.flywaydb.core.Flyway
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.jdbc.datasource.SingleConnectionDataSource

import java.time.LocalDate
import java.util.Random

import org.assertj.core.api.Java6Assertions.assertThat


class SQLProjectRepositoryTest {

    val dataSource = SingleConnectionDataSource("jdbc:mysql://localhost/burndown_test?serverTimezone=UTC&useSSL=false", "root", "", true)
    private val projectRepository: ProjectRepository = SQLProjectRepository(dataSource)
    private val flyway: Flyway = Flyway()

    @Before
    fun setup() {
        flyway.dataSource = dataSource
        flyway.migrate()

    }

    @After
    fun teardown() {
        flyway.clean()
    }

    @Test
    fun getAllProjectEntities() {
        val firstSavedEntity = projectRepository.save(generateRandomEntity())
        val secondSavedEntity = projectRepository.save(generateRandomEntity())

        val allProjectEntities = projectRepository.allProjectEntities()

        assertThat(allProjectEntities).hasSize(2)
        assertThat(allProjectEntities[0].id).isEqualTo(firstSavedEntity.id)
        assertThat(allProjectEntities[1].id).isEqualTo(secondSavedEntity.id)
    }

    @Test
    fun getProjectEntityById_returnsEntitySavedInDB() {
        val date = LocalDate.of(1999, 12, 31)
        val projectEntity = ProjectEntity(
                name = "test-name",
                startDate = date,
                hourlyRate = 750,
                budget = 150000
        )

        val save = projectRepository.save(projectEntity)

        val savedEntity = projectRepository.getProjectEntityById(save.id)

        assertThat(savedEntity!!.id).isEqualTo(1)
        assertThat(savedEntity.name).isEqualTo("test-name")
        assertThat(savedEntity.startDate).isEqualTo(date)
        assertThat(savedEntity.hourlyRate).isEqualTo(750)
        assertThat(savedEntity.budget).isEqualTo(150000)
    }

    @Test
    fun getProjectEntityById_returnsNullOnEmptyResultDataAccessException() {
        val ID_THAT_DOES_NOT_EXIST = 738
        val savedEntity = projectRepository.getProjectEntityById(ID_THAT_DOES_NOT_EXIST)

        assertThat(savedEntity).isNull()
    }

    @Test
    fun save_returnsFullyHydratedProjectEntity() {
        val projectEntity = ProjectEntity(
                name = "name-to-save",
                hourlyRate = 365,
                startDate = LocalDate.of(2018, 8, 30),
                budget = 25000
        )

        val savedProjectEntity = projectRepository.save(projectEntity)

        assertThat(savedProjectEntity.id).isEqualTo(1)
        assertThat(savedProjectEntity.name).isEqualTo("name-to-save")
        assertThat(savedProjectEntity.hourlyRate).isEqualTo(365)
        assertThat(savedProjectEntity.startDate).isEqualTo(LocalDate.of(2018, 8, 30))
        assertThat(savedProjectEntity.budget).isEqualTo(25000)
    }

    private fun generateRandomEntity(): ProjectEntity {
        val random = Random()

        val randomDate = LocalDate.of(
                random.nextInt(3000),
                random.nextInt(11) + 1,
                random.nextInt(27) + 1)

        return ProjectEntity(
                name = random.nextInt().toString(),
                startDate = randomDate,
                hourlyRate = random.nextInt(),
                budget = random.nextInt()
        )
    }
}