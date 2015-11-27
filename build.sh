#!/bin/bash

source ./environmentSetup.sh
export USER_CREATE_QUEUE="userCreateQueue"

#=========================================
#Start external apps
#start rabbitmq
rabbitmq-server -detached
#start smtp server

#=========================================
#Build & Run each Microservice

#Build via parent
mvn clean install $MVN_STRING -Dspring.profiles.active=test || { echo "Maven build unsuccessful for Sendash project"; exit 1; }

#Email Queue
cd registerUserQ/
java -jar ./target/sendEmailQueue-0.1.0.jar > /var/log/sendash/sendash_email.log &
cd ..

#Sendash API
cd sendash

#Deploy to tomcat
rm -rf /var/lib/tomcat7/webapps/sendash*
rm -rf /var/lib/tomcat7/work/Catalina/localhost/sendash
cp ./target/sendash-0.1.0.war $CATALINA_HOME/webapps/sendash.war
service tomcat7 restart
cd ..

#=========================================
#Run external integration tests
cd sendash

mvn clean test  -Dtest=com.addicks.sendash.admin.test.TestQ -Dspring.profiles.active=MicroserviceIntegrationTests || { echo "Maven build unsuccessful for External Integration Tests"; exit 1; }
cd ..

