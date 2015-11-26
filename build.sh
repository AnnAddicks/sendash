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

#Email Queue
cd registerUserQ/
mvn clean install || { echo "Maven build unsuccessful for Email Queue"; exit 1; }
java -jar ./target/sendEmailQueue-0.1.0.jar > /var/log/sendash/sendash_email.log &
cd ..

#Sendash API
cd sendash
mvn clean install $MVN_STRING -Dspring.profiles.active=test || { echo "Maven build unsuccessful for Sendash API"; exit 1; }

#Deploy to tomcat
cp ./target/sendash-0.1.0.war $CATALINA_HOME/webapps/sendash.war
cd ..

#=========================================
#Run external integration tests
cd sendash

echo "************************"
echo "USER QUEUE $USER_CREATE_QUEUE"
echo "************************"
mvn clean test  -Dtest=com.addicks.sendash.admin.test.TestQ -Dspring.profiles.active=MicroserviceIntegrationTests || { echo "Maven build unsuccessful for External Integration Tests"; exit 1; }
cd ..

