package com.phonecompany.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillCalculatorImpl implements TelephoneBillCalculator{

    @Override
    public BigDecimal calculate(String phoneLog) {
        String[] splitLog = phoneLog.split(",");
        String phoneNumber = splitLog[0];
        LocalDateTime callStart = getDate(splitLog[1]);
        LocalDateTime callEnd = getDate(splitLog[2]);
        BigDecimal bill = BigDecimal.valueOf(0.00);
        return null;
    }

    private LocalDateTime getDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
        return dateTime;
    }
}
