package de.mydomain.drools.factories;

import de.mydomain.drools.model.Customer;
import de.mydomain.drools.model.Order;
import de.mydomain.drools.model.OrderState;

public class ModelFactory {
    
    public static Order getOrderWithFiveHighRangeItems() {
        
        return new OrderBuilder(new Customer())
            .newLine()
                .withItem()
                    .withName("A")
                    .withCost(700.0)
                    .withSalePrice(800.0)
                .end()
                .withQuantity(1)
                .end()
            .end()
            .newLine()
                .withItem()
                    .withName("B")
                    .withCost(800.0)
                    .withSalePrice(850.0)
                .end()
                .withQuantity(2)
                .end()
            .end()
            .newLine()
                .withItem()
                    .withName("C")
                    .withCost(800.0)
                    .withSalePrice(850.0)
                .end()
                .withQuantity(3)
                .end()
            .end()
            .newLine()
                .withItem()
                    .withName("D")
                    .withCost(800.0)
                    .withSalePrice(850.0)
                .end()
                .withQuantity(4)
                .end()
            .end()    
            .newLine()
                .withItem()
                    .withName("E")
                    .withCost(800.0)
                    .withSalePrice(850.0)
                .end()
                .withQuantity(5)
                .end()
            .end()
        .build();
    }
    
    public static Order getPendingOrderWithTotalValueGreaterThan10000(Customer customer){
        return new OrderBuilder(customer)
            .withSate(OrderState.PENDING)
            .newLine()
                .withItem()
                    .withSalePrice(5000.0)
                .end()
                .withQuantity(2)
            .end()
            .newLine()
                .withItem()
                    .withSalePrice(800.0)
                .end()
                .withQuantity(5)
            .end()
        .build();
    }
    
    public static Order getPendingOrderWithTotalValueLessThan10000(Customer customer){
        return new OrderBuilder(customer)
            .withSate(OrderState.PENDING)
            .newLine()
                .withItem()
                    .withSalePrice(1000.0)
                .end()
                .withQuantity(1)
            .end()
        .build();
    }
}
