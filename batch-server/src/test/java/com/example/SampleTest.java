package com.example;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SampleTest {
    @Test
    public void makeDayList() {
        Date start = Date.valueOf(LocalDate.of(2017, 12, 1));
        Date end = Date.valueOf(LocalDate.of(2018, 2, 1));
        LocalDate startLocal = start.toLocalDate();
        LocalDate endLocal = end.toLocalDate();
        long days = ChronoUnit.DAYS.between(startLocal, endLocal);
        System.out.println(String.format("makeDayList days : %d", days));
        List<String> dayList = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            final String currDay = startLocal.format(DateTimeFormatter.ISO_DATE);
            dayList.add(currDay + " 00:00:00");
            startLocal = startLocal.plusDays(1);
        }
        System.out.println(String.format("makeDayList dayList : %s", dayList));
    }
}
