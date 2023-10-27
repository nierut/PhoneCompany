package com.phonecompany.billing;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public interface TelephoneBillCalculator {
    BigDecimal calculate (String phoneLog);

    BigDecimal calculateFirstFiveMinutes(LocalDateTime start, LocalDateTime end);

    boolean timeFallsIntoInterval(LocalTime time);

    String getFreeNumber(String fileName);

    String getMostOftenCalledNumber(HashMap<String, Integer> calledNumbers);

    LocalDateTime getDate(String dateStr);
}
