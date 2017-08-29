package io.pivotal.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ProjectEntity> getAllProjectEntities() {
        return jdbcTemplate.queryForList(
            "SELECT * FROM projects"
        ).stream()
            .map(r -> new ProjectEntity(
                (int) r.get("id"),
                (String) r.get("name"),
                ((Date) r.get("start_date")).toLocalDate(),
                (int) r.get("hourly_rate"),
                (int) r.get("budget")
            )).collect(toList());
    }

    ProjectEntity getProjectEntityById(int projectId) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM projects WHERE id = ?",
                getProjectEntityRowMapper(),
                projectId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    ProjectEntity save(ProjectEntity projectEntity) {
        jdbcTemplate.update(
            "INSERT INTO projects (name, start_date, hourly_rate, budget) VALUES (?, ?, ?, ?);",
            projectEntity.getName(),
            projectEntity.getStartDate(),
            projectEntity.getHourlyRate(),
            projectEntity.getBudget()
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
            LocalDate.parse(rs.getString("start_date")),
            rs.getInt("hourly_rate"),
            rs.getInt("budget")
        );
    }
}
