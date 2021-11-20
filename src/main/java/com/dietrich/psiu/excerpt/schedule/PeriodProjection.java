package com.dietrich.psiu.excerpt.schedule;

import com.dietrich.psiu.model.schedule.Period;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "periodProjection", types = Period.class)
public interface PeriodProjection {
}
