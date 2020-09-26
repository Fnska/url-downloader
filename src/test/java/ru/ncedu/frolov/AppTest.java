package ru.ncedu.frolov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    @Test
    public void trueIfTestsWork() {
        App app = new App();
        assertEquals("TESTS WORK!", app.test());
    }
}