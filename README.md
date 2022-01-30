# Creating a DROOLS project with Maven archetype

```txt
mvn -B archetype:generate -DarchetypeGroupId=org.kie -DarchetypeArtifactId=kie-drools-archetype -DarchetypeVersion=7.64.0.Final -DgroupId=de.mydomain -DartifactId=myfirst-drools-project -Dversion=1.0-SNAPSHOT -Dpackage=de.mydomain.drools
```

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

>  mvn exec:java -D"exec.mainClass"="de.mydomain.drools.App"
