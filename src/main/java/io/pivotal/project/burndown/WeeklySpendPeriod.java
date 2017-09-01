package io.pivotal.project.burndown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by devondapuzzo on 9/1/17.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class WeeklySpendPeriod {
    private Float weeklySpend;
    private Integer numberOfEmployees;
}

