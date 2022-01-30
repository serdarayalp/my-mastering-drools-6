package de.mydomain.drools;

import de.mydomain.drools.model.Item;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class App {

    public static void main(String[] args) {

        System.out.println("Bootstrapping the Rule Engine...");

        System.out.println("Bootstrapping a Rule Engine Session...");
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession();

        Item item = new Item("A", 123.0, 234.0);
        System.out.println("Item Category: " + item.getCategory());

        kieSession.insert(item);

        /*
        Item item2 = new Item("B", 250.0, 234.0);
        kieSession.insert(item2);
        Item item3 = new Item("C", 300.0, 234.0);
        kieSession.insert(item3);
        Item item4 = new Item("D", 100.0, 234.0);
        kieSession.insert(item4);
        */

        int fired = kieSession.fireAllRules();
        System.out.println("Number of Rules executed = " + fired);

        System.out.println("Item Category: " + item.getCategory());
    }
}
