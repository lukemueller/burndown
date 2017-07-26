package io.pivotal.project;

import io.pivotal.BurndownApplication;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {BurndownApplication.class})
@ActiveProfiles("test")
public class ProjectRepositoryTest {

    private ProjectRepository projectRepository;
    private Flyway flyway;

    @Autowired
    DataSource dataSource;

    @Before
    public void setup() {
        flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();

        projectRepository = new ProjectRepository(dataSource);
    }

    @After
    public void teardown() {
        flyway.clean();
    }

    @Test
    public void getAllProjectEntities() {
        ProjectEntity firstSavedEntity = projectRepository.save(generateRandomEntity());
        ProjectEntity secondSavedEntity = projectRepository.save(generateRandomEntity());

        List<ProjectEntity> allProjectEntities = projectRepository.getAllProjectEntities();

        assertThat(allProjectEntities).hasSize(2);
        assertThat(allProjectEntities.get(0).getId()).isEqualTo(firstSavedEntity.getId());
        assertThat(allProjectEntities.get(1).getId()).isEqualTo(secondSavedEntity.getId());
    }

    @Test
    public void getProjectEntityById_returnsEntitySavedInDB() {
        LocalDate date = LocalDate.of(1999, 12, 31);
        ProjectEntity projectEntity = ProjectEntity.builder()
            .name("test-name")
            .startDate(date)
            .hourlyRate(750)
            .budget(150000)
            .build();
        ProjectEntity save = projectRepository.save(projectEntity);

        ProjectEntity savedEntity = projectRepository.getProjectEntityById(save.getId());

        assertThat(savedEntity.getId()).isEqualTo(1);
        assertThat(savedEntity.getName()).isEqualTo("test-name");
        assertThat(savedEntity.getStartDate()).isEqualTo(date);
        assertThat(savedEntity.getHourlyRate()).isEqualTo(750);
        assertThat(savedEntity.getBudget()).isEqualTo(150000);
    }

    @Test
    public void getProjectEntityById_returnsNullOnEmptyResultDataAccessException() {
        int ID_THAT_DOES_NOT_EXIST = 738;
        ProjectEntity savedEntity = projectRepository.getProjectEntityById(ID_THAT_DOES_NOT_EXIST);

        assertThat(savedEntity).isNull();
    }

    @Test
    public void save_returnsFullyHydratedProjectEntity() {
        ProjectEntity projectEntity = ProjectEntity.builder()
            .name("name-to-save")
            .hourlyRate(365)
            .startDate(LocalDate.of(2018, 8, 30))
            .budget(25000)
            .build();

        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);

        assertThat(savedProjectEntity.getId()).isEqualTo(1);
        assertThat(savedProjectEntity.getName()).isEqualTo("name-to-save");
        assertThat(savedProjectEntity.getHourlyRate()).isEqualTo(365);
        assertThat(savedProjectEntity.getStartDate()).isEqualTo(LocalDate.of(2018, 8, 30));
        assertThat(savedProjectEntity.getBudget()).isEqualTo(25000);
    }

    private ProjectEntity generateRandomEntity() {
        Random random = new Random();

        LocalDate randomDate = LocalDate.of(
            random.nextInt(3000),
            random.nextInt(11) + 1,
            random.nextInt(27) + 1);

        return ProjectEntity.builder()
            .name(String.valueOf(random.nextInt()))
            .startDate(randomDate)
            .hourlyRate(random.nextInt())
            .budget(random.nextInt())
            .build();
    }
}