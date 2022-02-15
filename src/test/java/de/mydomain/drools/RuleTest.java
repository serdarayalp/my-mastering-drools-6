package de.mydomain.drools;

import de.mydomain.drools.model.Customer;
import de.mydomain.drools.model.Item;
import de.mydomain.drools.model.Order;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.command.Command;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RuleTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void testMessageRuleRuntime() {

        String ruleContent = "package de.mydomain.drools.rules;\n" +
                "\n" +
                "import de.mydomain.drools.model.Item;\n" +
                "import de.mydomain.drools.model.Item.Category;\n" +
                "\n" +
                "rule \"Classify Item - High Range\"\n" +
                "when\n" +
                "$i: Item(cost > 200)\n" +
                "then\n" +
                "$i.setCategory(Category.HIGH_RANGE);\n" +
                "end\n";

        KieSession kieSession = null;

        try {
            KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            knowledgeBuilder.add(ResourceFactory.newByteArrayResource(ruleContent.getBytes(StandardCharsets.UTF_8)), ResourceType.DRL);

            /*
            KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
            for (KnowledgeBuilderError error : errors) {
                System.out.println(error);
            }
            */

            InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
            knowledgeBase.addPackages(knowledgeBuilder.getKnowledgePackages());
            kieSession = knowledgeBase.newKieSession();

            Item item = new Item("A", 250.0, 234.0);
            System.out.println("Item Category: " + item.getCategory());

            kieSession.insert(item);

            int fired = kieSession.fireAllRules();
            System.out.println("Number of Rules executed = " + fired);

            System.out.println("Item Category: " + item.getCategory());
        } finally {
            if (kieSession != null) {
                kieSession.dispose();
            }
        }
    }

    @Test
    public void statelessSessionTest() {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();

        StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession("rules.simple.discount.stateless");

        Assert.assertNotNull(statelessKieSession);

        Customer customer = new Customer();
        customer.setCategory(Customer.Category.SILVER);

        Order order = new Order();
        order.setCustomer(customer);

        Command newInsertOrder = kieServices.getCommands().newInsert(order, "orderOut");
        Command newInsertCustomer = kieServices.getCommands().newInsert(customer);
        Command newFireAllRules = kieServices.getCommands().newFireAllRules("outFired");

        List<Command> commandList = new ArrayList<>();

        commandList.add(newInsertOrder);
        commandList.add(newInsertCustomer);
        commandList.add(newFireAllRules);

        ExecutionResults executionResults = statelessKieSession.execute(kieServices.getCommands()
                .newBatchExecution(commandList));

        order = (Order) executionResults.getValue("orderOut");
        int fired = (Integer) executionResults.getValue("outFired");

        assertEquals(2, fired);
        assertEquals(10.0, order.getDiscount().getPercentage(), 0.0);
    }
}