package com.attractorschool.imurab.dto.queue;

public class Hour {
    private int hour;
    private boolean[] notAllowed;


    public Hour(int hour, int maxParallelIrrigation) {
        this.hour = hour;
        this.notAllowed = new boolean[maxParallelIrrigation];
    }

    public int getHour() {
        return hour;
    }
    public int getArraySize(){
        return this.notAllowed.length;
    }

    public boolean isFree() {
        return !this.notAllowed[notAllowed.length-1];
    }

    public void take(){
        for (int i = 0; i < this.notAllowed.length; i++) {
         if (this.notAllowed[i]){
             continue;
         }
         this.notAllowed[i] = true;
         return;
        }
    }

    public void release(){
        for (int i = this.notAllowed.length; i > 0;  i--) {
            if (!this.notAllowed[i-1]){
                continue;
            }
            this.notAllowed[i-1] = false;
            return;
        }
    }
}
