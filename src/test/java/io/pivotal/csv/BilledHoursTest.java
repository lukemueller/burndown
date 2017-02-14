package io.pivotal.csv;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


public class BilledHoursTest {

    @Test
    public void getTotalHours() {
        BilledHours billedHours = new BilledHours();

        billedHours.setHours(Arrays.asList(5l, 5l, 5l));
        assertThat(billedHours.getTotalHours()).isEqualTo(15l);

        billedHours.setHours(Arrays.asList(10l, 10l, 10l));
        assertThat(billedHours.getTotalHours()).isEqualTo(30l);
    }
}