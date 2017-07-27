package io.pivotal.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.Date
import java.sql.ResultSet
import java.time.LocalDate
import javax.sql.DataSource

interface ProjectRepository {
    fun allProjectEntities() : List<ProjectEntity>
    fun getProjectEntityById(projectId: Int): ProjectEntity?
    fun save(projectEntity: ProjectEntity): ProjectEntity
}

@Component
internal open class SQLProjectRepository @Autowired
constructor(dataSource: DataSource) : ProjectRepository {

    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    override fun allProjectEntities() : List<ProjectEntity> =
        jdbcTemplate.queryForList(
                "SELECT * FROM projects"
        ).toList()
                .map({ r ->
                    ProjectEntity(
                            r.get("id") as Int,
                            r.get("name") as String,
                            (r.get("start_date") as Date).toLocalDate(),
                            r.get("hourly_rate") as Int,
                            r.get("budget") as Int
                    )
                })

    override fun getProjectEntityById(projectId: Int): ProjectEntity? {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM projects WHERE id = ?",
                    projectEntityRowMapper,
                    projectId
            )
        } catch (e: EmptyResultDataAccessException) {
            return null
        }

    }

    override fun save(projectEntity: ProjectEntity): ProjectEntity {
        jdbcTemplate.update(
                "INSERT INTO projects (name, start_date, hourly_rate, budget) VALUES (?, ?, ?, ?);",
                projectEntity.name,
                projectEntity.startDate,
                projectEntity.hourlyRate,
                projectEntity.budget
        )

        return getProjectEntityByNameAndStateDateAndHourlyRate(projectEntity)
    }

    private fun getProjectEntityByNameAndStateDateAndHourlyRate(projectEntity: ProjectEntity): ProjectEntity {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM projects WHERE name = ? AND start_date = ? AND hourly_rate = ?",
                projectEntityRowMapper,
                projectEntity.name,
                projectEntity.startDate,
                projectEntity.hourlyRate
        )
    }

    private val projectEntityRowMapper: RowMapper<ProjectEntity> =  RowMapper()
        { rs : ResultSet, _ : Int ->
            ProjectEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("start_date")),
                    rs.getInt("hourly_rate"),
                    rs.getInt("budget")
            )
        }
}
