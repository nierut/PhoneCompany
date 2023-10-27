package com.phonecompany.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
}