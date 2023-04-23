package no.uib.inf101.sem2.bomberman.model;

public class Clock {

    private int minutes;
    private int seconds;

    public Clock() {
        this.minutes = 2;
        this.seconds = 00;
    }

    void tick() {
        if (this.seconds == 0) {
            this.seconds = 59;
            this.minutes--;
        } else {
            this.seconds--;
        }
    }

    int getTime() {
        return this.minutes * 60 + this.seconds;
    }

    int getMinutes() {
        return this.minutes;
    }

    int getSeconds() {
        return this.seconds;
    }

    void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    void setSeconds(int seconds) {
        this.seconds = seconds;
    }

}
