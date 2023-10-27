package com.phonecompany.billing;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BillCalculatorImpl implements TelephoneBillCalculator{
    private String CSVFileName;

    @Override
    public BigDecimal calculate(String phoneLog) {
        String[] splitLog = phoneLog.split(",");
        String phoneNumber = splitLog[0];
        LocalDateTime callStart = getDate(splitLog[1]);
        LocalDateTime callEnd = getDate(splitLog[2]);
        String freeNumber = getFreeNumber(CSVFileName);
        if(freeNumber.equals(phoneNumber)){
            return BigDecimal.valueOf(0.00);
        }
        Double callLength = (double) callStart.until(callEnd, ChronoUnit.MINUTES);
        BigDecimal bill = calculateFirstFiveMinutes(callStart,callEnd);
        if(callLength <= 5.0) {
            return bill;
        }
        Double restOfCall = callLength - 5.0;
        int restOfMinutes = (int) Math.ceil(restOfCall);
        BigDecimal priceOfRemainderOfCall = BigDecimal.valueOf(restOfMinutes * 0.20);
        return bill.add(priceOfRemainderOfCall);
    }

    private BigDecimal calculateFirstFiveMinutes(LocalDateTime start, LocalDateTime end) {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        LocalDateTime currentTime = start;
        int counter = 0;
        while(counter < 5 && currentTime.isBefore(end)) {
        LocalTime time = currentTime.toLocalTime();
        if(timeFallsIntoInterval(time)) {
            sum.add(BigDecimal.valueOf(1.0));
        } else {
            sum.add(BigDecimal.valueOf(0.50));
        }
        counter++;
        currentTime = currentTime.plusMinutes(1);
        }
        return sum;
    }

    private boolean timeFallsIntoInterval(LocalTime time) {
        LocalTime start = LocalTime.of(8, 0, 0);
        LocalTime end = LocalTime.of(16, 0, 1);
        if(time.isAfter(start) && time.isBefore(end)) {
            return true;
        }
        return false;
    }

    public String getFreeNumber(String fileName) {
        Path filePath = Paths.get(fileName);

        try {
            List<String> lines = Files.readAllLines(filePath);
            HashMap<String, Integer> calledNumbers = new HashMap<>();
            for(int i = 0; i < lines.size();i++) {
                String number = lines.get(i).split(",")[0];
                if(calledNumbers.containsKey(number)) {
                    calledNumbers.put(number,calledNumbers.get(number) + 1);
                } else {
                    calledNumbers.put(number,1);
                }
            }
            String freeNumber = getMostOftenCalledNumber(calledNumbers);
            return freeNumber;
        }catch (IOException exception){
            return "";
        }
    }

    private String getMostOftenCalledNumber(HashMap<String, Integer> calledNumbers) {
        Integer highestNumberOfCalls = 0;
        for (Map.Entry<String, Integer> set :
                calledNumbers.entrySet()) {
            if(set.getValue() > highestNumberOfCalls) {
                highestNumberOfCalls = set.getValue();
            }
        }
        List<String> mostCalledNumbers = new ArrayList<>();
        for (Map.Entry<String, Integer> set :
                calledNumbers.entrySet()) {
            if(set.getValue() == highestNumberOfCalls) {
                mostCalledNumbers.add(set.getKey());
            }
        }
        if(mostCalledNumbers.size() == 1) {
            return mostCalledNumbers.get(0);
        }
            List<Integer> arithmeticNumbers = new ArrayList<>();
            for(int i = 0; i < mostCalledNumbers.size(); i++) {
                arithmeticNumbers.add(Integer.valueOf(mostCalledNumbers.get(i)));
            }
            Collections.sort(arithmeticNumbers);
            return String.valueOf(arithmeticNumbers.get(arithmeticNumbers.size() - 1));
    }
    private LocalDateTime getDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
        return dateTime;
    }
}
