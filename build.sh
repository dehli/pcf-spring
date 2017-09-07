#!/usr/bin/env bash

pcf_cloud=true

# kotlin
cd kotlin
gradle wrapper --gradle-version 3.5.1
./gradlew assemble

# accounts
cd ../accounts
gradle wrapper --gradle-version 3.5.1
./gradlew assemble
if [ "$pcf_cloud" = true ] ; then
  cf create-service cleardb spark accounts-mysql
else
  cf create-service p-mysql 1gb accounts-mysql
fi

# transactions
cd ../transactions
gradle wrapper --gradle-version 3.5.1
./gradlew assemble
if [ "$pcf_cloud" = true ] ; then
  cf create-service cleardb spark transactions-mysql
else
  cf create-service p-mysql 1gb transactions-mysql
fi

# shared services
if [ "$pcf_cloud" = true ] ; then
  cf create-service cloudamqp lemur events-queue
  cf create-service rediscloud 30mb shared-redis
else
  cf create-service p-rabbitmq standard events-queue
  cf create-service p-redis shared-vm shared-redis
fi

cf create-service -c '{ "git": { "uri": "https://github.com/dehli/pcf-spring.git", "label": "master"  } }' p-config-server standard config-server
