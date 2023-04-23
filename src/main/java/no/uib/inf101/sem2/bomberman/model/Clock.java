package no.uib.inf101.sem2.bomberman.model;

/**
 * A clock
 * 
 */
public class Clock {

    private int minutes;
    private int seconds;

    /**
     * Creates a new clock
     * 
     * @param minutes the minutes
     * @param seconds the seconds
     */
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

    /**
     * Gets the time in seconds
     * 
     * @return the time in seconds
     */
    int getTime() {
        return this.minutes * 60 + this.seconds;
    }

    /**
     * Gets the minutes
     * 
     * @return the minutes
     */
    int getMinutes() {
        return this.minutes;
    }

    /**
     * Gets the seconds
     * 
     * @return the seconds
     */
    int getSeconds() {
        return this.seconds;
    }

    /**
     * Sets the minutes
     * 
     * @param minutes the minutes
     */
    void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Sets the seconds
     * 
     * @param seconds the seconds
     */
    void setSeconds(int seconds) {
        this.seconds = seconds;
    }

}
