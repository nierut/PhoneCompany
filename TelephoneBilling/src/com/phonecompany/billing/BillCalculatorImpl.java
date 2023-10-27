package com.phonecompany.billing;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        BigDecimal bill = BigDecimal.valueOf(0.00);
        return null;
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
            return null;
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
