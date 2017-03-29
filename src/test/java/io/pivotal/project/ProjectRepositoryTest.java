package io.pivotal.project;

import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class ProjectRepositoryTest {

    private ProjectRepository projectRepository;
    private Flyway flyway;

    @Before
    public void setup() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/burndown_test?serverTimezone=UTC&useSSL=false", "root", "", true);
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
        ProjectEntity projectEntity = new ProjectEntity("test-name", LocalDate.of(1999, 12, 31), 750, 150000);
        ProjectEntity save = projectRepository.save(projectEntity);

        ProjectEntity savedEntity = projectRepository.getProjectEntityById(save.getId());

        assertThat(savedEntity.getId()).isEqualTo(1);
        assertThat(savedEntity.getName()).isEqualTo("test-name");
        assertThat(savedEntity.getStartDate()).isEqualTo(LocalDate.of(1999, 12, 31));
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
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName("name-to-save");
        projectEntity.setHourlyRate(365);
        projectEntity.setStartDate(LocalDate.of(2018, 8, 30));
        projectEntity.setBudget(25000);

        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);

        assertThat(savedProjectEntity.getId()).isEqualTo(1);
        assertThat(savedProjectEntity.getName()).isEqualTo("name-to-save");
        assertThat(savedProjectEntity.getHourlyRate()).isEqualTo(365);
        assertThat(savedProjectEntity.getStartDate()).isEqualTo(LocalDate.of(2018, 8, 30));
        assertThat(savedProjectEntity.getBudget()).isEqualTo(25000);
    }

    private ProjectEntity generateRandomEntity() {
        Random random = new Random();

        return new ProjectEntity(
            String.valueOf(random.nextInt()),
            LocalDate.of(
                random.nextInt(3000),
                random.nextInt(11) + 1,
                random.nextInt(27) + 1),
            random.nextInt(),
            random.nextInt()
        );
    }
}