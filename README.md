# Creating a DROOLS project with Maven archetype

```txt
mvn -B archetype:generate -DarchetypeGroupId=org.kie -DarchetypeArtifactId=kie-drools-archetype -DarchetypeVersion=7.64.0.Final -DgroupId=de.mydomain -DartifactId=myfirst-drools-project -Dversion=1.0-SNAPSHOT -Dpackage=de.mydomain.drools
```

# kmodule.xml
The kmodule.xml file is used to customize the KieModule configurations. In this
file, we can define how the rules are grouped together in different KieBases that
can be loaded for different purposes. It also allows us to define more fine-grained
configurations for the rule engine instance that will be created.

Using the concepts of KieBase and KieSession, we can define the granularity of how
the rules will need to be loaded. A KieBase represents a compiled version of a set of
assets, and a KieSession represents an instance of the rule engine containing the rules
in the KieBase. For this reason, it makes sense to have multiple sessions defined with
different configurations for the same KieBase. In other words, we can use the same
rules, but have a session configured in a different way for different needs.

The stateful KieSession allows us to keep the state between several interactions with the Rule Engine. In Drools 6, Stateful Knowledge Sessions were renamed to KieSessions, as there are the most common type of session, the name was kept short. In contrast, StatelessKieSession only allows us to interact once, take the results out, and no state is stored for the next interaction.

# KieSession
KieSession represents a  running instance of the Rule Engine with a specific configuration and set of  rules.
It holds the evaluation algorithm used to match the rules against our  domain objects.


# Starting the program

You will need to compile the project in order to execute this class and you
can do this by executing from the terminal or your IDE, as follows:

> mvn clean install

This will compile and package your project, look for the Build Success output in the
terminal. After executing this line, you will find the /target directory containing
a jar file that you can use to execute the previously compiled class using the following line:

>  mvn exec:java -D"exec.mainClass"="de.mydomain.drools.MainItemCategory"


# pom.xml

```txt
<dependencies>
    <dependency>
        <groupId>org.kie</groupId>
        <artifactId>kie-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.drools</groupId>
        <artifactId>drools-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.drools</groupId>
        <artifactId>drools-compiler</artifactId>
    </dependency>
</dependencies>
```

The first **org.kie:kie-api** dependency contains all the public interfaces exposed
by the KIE Platform, which is composed by Drools, jBPM, and OptaPlanner. Next,
we include the **org.drools:drools-core** artifact, which contains the Drools rule
engine implementation. Finally, we will include the **org.drools:drools-compiler**
artifact that contains the algorithm to translate the rules written in different resources
(text files, spreadsheets, your own types, and so on) to executable rules. This artifact
is required only because we are compiling our rules in the project.
