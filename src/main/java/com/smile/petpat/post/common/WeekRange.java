package com.smile.petpat.post.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Getter
public class WeekRange {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startOfWeek;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endOfWeek;

    public WeekRange() {
        this.startOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY);
        this.endOfWeek = LocalDateTime.now().with(DayOfWeek.SUNDAY);
    }


    public String getFormattedStartOfWeek() {
        return startOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getFormattedEndOfWeek() {
        return endOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

