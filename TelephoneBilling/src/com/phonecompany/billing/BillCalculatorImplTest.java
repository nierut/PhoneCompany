package com.phonecompany.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BillCalculatorImplTest {

    BillCalculatorImpl calculator = new BillCalculatorImpl();

    @Test
    void calculatesFirstFiveMinutesCorrectly() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 13, 18, 10, 15);
        LocalDateTime end = LocalDateTime.of(2020,1,13,18,12,57);
        BigDecimal expected = BigDecimal.valueOf(3 * 0.50);
        BigDecimal actual = calculator.calculateFirstFiveMinutes(start,end);
        assertEquals(expected, actual);
    }

    @Test
    void doesTimeFallIntoInterval() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 13, 18, 10, 15);
        LocalTime time = start.toLocalTime();
        assertTrue(calculator.timeFallsIntoInterval(time));
    }


}