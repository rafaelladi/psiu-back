package com.dietrich.psiu.excerpt.schedule;

import com.dietrich.psiu.model.schedule.Period;
import com.dietrich.psiu.model.schedule.Schedule;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.*;
import java.util.Date;

@Projection(name = "periodProjection", types = Period.class)
public interface PeriodProjection {
    Long getId();
    Integer getDayOfWeek();
    Date getStart();
    Date getFinish();
}
