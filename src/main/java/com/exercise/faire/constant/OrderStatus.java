package com.exercise.faire.constant;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author eder
 */
public enum OrderStatus {
    
    NEW,
    PROCESSING,
    PRE_TRANSIT,
    IN_TRANSIT,
    DELIVERED,
    BACKORDERED,
    CANCELED;
    
    public static String getAllNonNewStatus() {
        
        final String allStatus = Arrays.asList(OrderStatus.values())
                .stream()
                .filter(status -> !status.equals(NEW))
                .map(status ->  status.name())
                .collect(Collectors.joining(","));
        
        return "[".concat(allStatus).concat("]");
        
    }
}
