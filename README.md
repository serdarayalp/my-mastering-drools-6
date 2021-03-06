# Creating a DROOLS project with Maven archetype

```txt
mvn -B archetype:generate -DarchetypeGroupId=org.kie -DarchetypeArtifactId=kie-drools-archetype -DarchetypeVersion=7.64.0.Final -DgroupId=de.mydomain -DartifactId=myfirst-drools-project -Dversion=1.0-SNAPSHOT -Dpackage=de.mydomain.drools
```

# Some important dependencies

```txt
<dependencies>
    ......
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
    ......
</dependencies>
```

The first **org.kie:kie-api** dependency contains all the public interfaces exposed
by the KIE Platform, which is composed by Drools, jBPM, and OptaPlanner. Next,
we include the **org.drools:drools-core** artifact, which contains the Drools rule
engine implementation. Finally, we will include the **org.drools:drools-compiler**
artifact that contains the algorithm to translate the rules written in different resources
(text files, spreadsheets, your own types, and so on) to executable rules. This artifact
is required only because we are compiling our rules in the project.

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

# KieSession
KieSession represents a  running instance of the Rule Engine with a specific configuration and set of  rules.
It holds the evaluation algorithm used to match the rules against our  domain objects.

# KieScanner
Every time our business logic (rules) changes, any KieContainer that referenced it has to be notified. One of the limitations of this approach is that we have to manually notify each of the applications that depend on the modified KieModule. What if there were a way to automatically let out the applications to be notified when a KieModule that they depend on gets updated? Fortunately for us, Drools provides this mechanism out of the box, its name is KieScanner.

```text
In order to use KieScanner in our application, the org.kie::kie-ci artifact must be added to the application's classpath.
```

# Rule attributes
```text
enabled false
```
If the enabled attribute is set to false, the rule will be evaluated, however, it won't  be executed.

```text
salience 10
```
If we want a rule to take precedence, we can set a higher priority to it using the salience rule attribute. The higher the salience, the higher the priority of the rule.

By default, all rules have an implicit salience attribute of 0 and you can assign  positive or negative values to the salience attribute in order to execute them before  or after the rest of the rules. Please take into account that rule attributes will only be  evaluated after the rule conditions have matched with a group of data in the working  memory, therefore, if the rule was not going to be triggered with the existing data, it won't be triggered regardless of how high or low the salience value is set.

```text
no-loop
```
a rule attribute exists to let the engine know that a  specific rule should not re-evaluate itself after it modifies the working memory.

```text
agenda-group "NAME_OF_GROUP"
```
Grouping for rules. When a rule is fired, it can also define the agenda  group that is going to be activated through the implicit global variable called **kcontext**

```text
ruleflow-group
```
There is a tool for Business Process Management (BPM) called jBPM, which uses
Drools as its base API and rule engine. It allows the end users to define diagrams to
show the sequence of steps in a process. Some of these steps can be rule executions
and to determine the rules that should fire at that particular point, they use a
common attribute between the rule step in the process and the rules that are going to
be invoked: the **ruleflow-group** rule attribute.

```text
activation-group
```
Groupings are used to split rules in groups; however, sometimes, we need these
groups to have an even more specific behavior. For example, we might define that a
specific group of rules should be mutually exclusive. To define such behavior, Drools
defines a rule attribute called activation-group, which defines that only one rule
should fire in that group. If the data that we feed the KieSession matches five rules in
the same activation group, the first one to fire will cancel the other four rules.

```text
...
then
    kcontext.getKieRuntime().getAgenda().getAgendaGroup("MAIN").setFocus();
```
when a rule is fired, it can also define the agenda group that is going to be activated through the implicit global variable called kcontext.

```text
date-effective "01-Jan-2015"
date-expires "31-Dec-2020"
```
date-effective and date-expires, determine the start and end date for a specific rule to be enabled. The **dd-mmm-yyyy** date format is supported by default. You can customize this by providing an alternative date format mask as a drools.dateformat system  property.

```text
calendars "weekdays"
timer (int:0 1h)

OR

calendars "weekends"
timer (cron:0 0 0/8 * * ?)


..............
..............
// Calendar weekDayCal = QuartzHelper.quartzCalendarAdapter(org.quartz.Calendar quartzCal);
// kieSession.getCalendars().set( "weekday", weekDayCal );

kieSession.getCalendars().set("weekends", new Calendar() {
    @Override
    public boolean isTimeIncluded(long timestamp) {
        return false;
    }
});
kieSession.getCalendars().set("weekdays", new Calendar() {
    @Override
    public boolean isTimeIncluded(long timestamp) {
        return true;
    }
});
..............
..............

---------------------------------------------------------------

timer ( int: <initial delay> <repeat interval>? )

timer ( int: 30s )
timer ( int: 30s 5m )

timer ( cron: <cron expression> )
timer ( cron:* 0/15 * * * ? )
```
Interval, indicated by **int:**, timers follow the semantics of java.util.Timer objects, with an initial delay and an **optional** repeat interval. Cron, indicated by **cron:**, timers follow standar  Unix cronexpressions.
