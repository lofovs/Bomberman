package no.uib.inf101.sem2.bomberman.model;

public class Clock {

    private int minutes;
    private int seconds;

    public Clock() {
        this.minutes = 1;
        this.seconds = 00;
    }

    public void tick() {
        if (this.seconds == 0) {
            this.seconds = 59;
            this.minutes--;
        } else {
            this.seconds--;
        }
    }

    public int getTime() {
        return this.minutes * 60 + this.seconds;
    }
}
