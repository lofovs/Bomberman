package no.uib.inf101.sem2.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestClock {

    @Test
    public void testConstructor() {
        Clock clock = new Clock();
        assertEquals(2, clock.getMinutes());
        assertEquals(0, clock.getSeconds());
    }

    @Test
    public void testTickSeconds() {
        Clock clock = new Clock();
        clock.setSeconds(10);
        clock.tick();
        assertEquals(9, clock.getSeconds());
    }

    @Test
    public void testTickMinutes() {
        Clock clock = new Clock();
        clock.setMinutes(1);
        clock.setSeconds(0);
        clock.tick();
        assertEquals(0, clock.getMinutes());
        assertEquals(59, clock.getSeconds());
    }

    @Test
    public void testTickZero() {
        Clock clock = new Clock();
        clock.setMinutes(0);
        clock.setSeconds(1);
        clock.tick();
        assertEquals(0, clock.getMinutes());
        assertEquals(0, clock.getSeconds());
    }

    @Test
    public void testGetTime() {
        Clock clock = new Clock();
        clock.setMinutes(1);
        clock.setSeconds(30);
        assertEquals(90, clock.getTime());
    }
}
