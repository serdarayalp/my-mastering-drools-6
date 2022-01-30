package de.mydomain.drools;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import de.mydomain.drools.model.Item;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}