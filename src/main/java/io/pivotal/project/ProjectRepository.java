package io.pivotal.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    ProjectEntity getProjectEntityById(int projectId) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM projects WHERE id = ?",
            getProjectEntityRowMapper(),
            projectId
        );
    }

    ProjectEntity save(ProjectEntity projectEntity) {
        jdbcTemplate.update(
            "INSERT INTO projects (name, start_date, hourly_rate) VALUES (?, ?, ?);",
            projectEntity.getName(),
            projectEntity.getStartDate(),
            projectEntity.getHourlyRate()
        );

        return getProjectEntityByNameAndStateDateAndHourlyRate(projectEntity);
    }

    private ProjectEntity getProjectEntityByNameAndStateDateAndHourlyRate(ProjectEntity projectEntity) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM projects WHERE name = ? AND start_date = ? AND hourly_rate = ?",
            getProjectEntityRowMapper(),
            projectEntity.getName(),
            projectEntity.getStartDate(),
            projectEntity.getHourlyRate()
        );
    }

    private RowMapper<ProjectEntity> getProjectEntityRowMapper() {
        return (rs, rowNum) -> new ProjectEntity(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getDate("start_date").toLocalDate(),
            rs.getInt("hourly_rate")
        );
    }
}
