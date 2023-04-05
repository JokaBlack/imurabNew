package com.attractorschool.imurab.dto.queue;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Day {
    private LocalDate date;
    private Hour[] hours = new Hour[24];

    public Day(LocalDate date, int maxParallelIrrigation) {
        this.date = date;
        makeHours(maxParallelIrrigation);
    }
    private void makeHours(int maxParallelIrrigation){
        for (int i = 0; i < hours.length; i++) {
          this.hours[i]= new Hour(i, maxParallelIrrigation);
        }
    }


}
