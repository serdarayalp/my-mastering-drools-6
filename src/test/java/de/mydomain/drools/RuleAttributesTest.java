package de.mydomain.drools;

import de.mydomain.drools.model.Item;
import de.mydomain.drools.model.OrderLine;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleAttributesTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleAttributesTest.class);

    @Test
    public void ruleAttributesTest() {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();

        KieSession kieSession = kieContainer.newKieSession("rules.attributes");

        Item item = new Item("A", 350.0, 234.0);

        System.out.println("Item Category: " + item.getCategory());

        kieSession.insert(item);

        kieSession.fireAllRules();

        System.out.println("Item Category: " + item.getCategory());
    }

    @Test
    public void agendaGroupTest() {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();

        KieSession kieSession = kieContainer.newKieSession("rules.attributes");
        kieSession.getAgenda().getAgendaGroup("promotions").setFocus();

        Item item = new Item("pencil", 350.0, 234.0);

        OrderLine orderLine = new OrderLine();
        orderLine.setItem(item);
        orderLine.setQuantity(20);

        kieSession.insert(orderLine);
        kieSession.fireAllRules();
    }

}