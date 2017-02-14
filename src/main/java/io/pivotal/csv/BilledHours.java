package io.pivotal.csv;

import java.util.Date;
import java.util.List;

public class BilledHours {
    private Date startDate;
    private List<Long> hours;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Long> getHours() {
        return hours;
    }

    public void setHours(List<Long> hours) {
        this.hours = hours;
    }

    public long getTotalHours() {
        return hours.stream().mapToLong(i -> i).sum();
    }
}
