#!/bin/bash

source ./environmentSetup.sh
export USER_CREATE_QUEUE="userCreateQueue"

#========================================
#Shutdown tomcat
$CATALINA_HOME/bin/shutdown.sh

#=========================================
#Start external apps
#start rabbitmq
rabbitmq-server -detached
#start smtp server

#=========================================
#Build & Run each Microservice
SKIP_TEST=false
if [[ $@ == *skip* ]]
  then 
    $MVN_STRING="-Dmaven.test.skip=true"
    $SKIP_TEST=true
fi

#Build via parent
mvn clean install $MVN_STRING -Dspring.profiles.active=test || { echo "Maven build unsuccessful for Sendash project"; exit 1; }

#Email Queue
cd sendEmailQueue/
java -jar ./target/sendEmailQueue-0.1.0.jar > /var/log/sendash/sendash_email.log &
cd ..


#=========================================
#Run external integration tests
cd sendashApi

if $SKIP_TEST==false 
then
  mvn test -DfailIfNoTests=false -Dtest=com.addicks.sendash.admin.test.TestQ -Dspring.profiles.active=MicroserviceIntegrationTests || { echo "Maven build unsuccessful for External Integration Tests"; exit 1; }
  cd ..
fi
#=========================================
#Deploy to tomcat
cd sendashApi
rm -rf $CATALINA_HOME/webapps/sendash*
rm -rf $CATALINA_HOME/work/Catalina/localhost/sendash
cp ./target/sendash-0.1.0.war $CATALINA_HOME/webapps/sendash.war
$CATALINA_HOME/bin/startup.sh
cd ..


