package com.dietrich.psiu.excerpt.schedule;

import com.dietrich.psiu.model.schedule.Schedule;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "scheduleProjection", types = Schedule.class)
public interface ScheduleProjection {
}
