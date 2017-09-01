package io.pivotal.project.burndown;

import io.pivotal.project.Project;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by devondapuzzo on 8/31/17.
 */
public class BurndownProjectionModelTest {

    private Project project;

    public void setup(int startingBudget){
        ArrayList<BurndownPeriod> startingBurndown = new ArrayList<>();
        BurndownPeriod startingState = new BurndownPeriod(LocalDate.now(),startingBudget, 1);
        startingBurndown.add(startingState);

        project = new Project();
        project.setStartDate(startingState.getDate());
        project.setBudget((int) startingState.getBudgetRemaining());
        project.setBurndown(startingBurndown);
        project.setHourlyRate(1);
        project.setNumberOfEmployees(1);
    }

    @Test
    public void buildProjection_buildsOutProjectionCorrectlyWhenEndsOnAFriday(){
        setup(80);

        BurndownProjectionModel.buildOut(project);
        assertThat(project.getProjectedEndDate()).isEqualTo(LocalDate.now().plusDays(5+2+5));

        project.setNumberOfEmployees(2);
        BurndownProjectionModel.buildOut(project);
        assertThat(project.getProjectedEndDate()).isEqualTo(LocalDate.now().plusDays(5));
    }


    @Test
    public void buildProjection_buildsOutProjectionCorrectlyWhenEndsOnAMonday(){
        setup(89);

        BurndownProjectionModel.buildOut(project);
        assertThat(project.getProjectedEndDate()).isEqualTo(LocalDate.now().plusDays(5+2+5+2+1));
    }

    @Test
    public void buildProjection_buildsOutProjectionCorrectlyWhenEndsMidWeek(){
        setup(105);

        BurndownProjectionModel.buildOut(project);
        assertThat(project.getProjectedEndDate()).isEqualTo(LocalDate.now().plusDays(5+2+5+2+3));
    }

}