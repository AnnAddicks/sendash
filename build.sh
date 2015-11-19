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
mvn clean install
java -jar ./target/sendEmailQueue-0.1.0.jar > /var/log/sendash_email &
cd ..

#Sendash API
cd sendash
mvn clean install $MVN_STRING
cp ./target/sendash-0.1.0.war $CATALINA_HOME/webapps/sendash.war
cd ..

#=========================================
#Run external integration tests
