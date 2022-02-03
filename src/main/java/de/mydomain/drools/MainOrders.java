package de.mydomain.drools;

import de.mydomain.drools.factories.ModelFactory;
import de.mydomain.drools.model.Item;
import de.mydomain.drools.model.Order;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Collection;

public class MainOrders {

    public static void main(String[] args) {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ordersSession");

        Order order = ModelFactory.getOrderWithFiveHighRangeItems();

        kieSession.insert(order.getCustomer());

        kieSession.insert(order.getOrderLines().get(0));
        kieSession.insert(order.getOrderLines().get(1));
        kieSession.insert(order.getOrderLines().get(2));
        kieSession.insert(order.getOrderLines().get(3));
        kieSession.insert(order.getOrderLines().get(4));

        kieSession.insert(order.getOrderLines().get(0).getItem());
        kieSession.insert(order.getOrderLines().get(1).getItem());
        kieSession.insert(order.getOrderLines().get(2).getItem());
        kieSession.insert(order.getOrderLines().get(3).getItem());
        kieSession.insert(order.getOrderLines().get(4).getItem());

        kieSession.insert(order);

        System.out.println("Category of order is = " + order.getCustomer().getCategory().name());

        int fired = kieSession.fireAllRules();

        System.out.println("Number of Rules executed = " + fired);
        System.out.println("Category of order after fireAllRules() is = " + order.getCustomer().getCategory().name());
    }
}
