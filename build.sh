#!/usr/bin/env bash

# kotlin
cd kotlin
gradle wrapper --gradle-version 3.5.1
./gradlew assemble

# accounts
cd ../accounts
gradle wrapper --gradle-version 3.5.1
./gradlew assemble
cf create-service p-mysql 1gb accounts-mysql
cf create-service p-redis shared-vm accounts-redis

# transactions
cd ../transactions
gradle wrapper --gradle-version 3.5.1
./gradlew assemble
cf create-service p-mysql 1gb transactions-mysql
cf create-service p-redis shared-vm transactions-redis

# shared services
cf create-service p-rabbitmq standard events-queue
cf create-service -c '{ "git": { "uri": "https://github.com/dehli/pcf-spring.git", "label": "master"  } }' p-config-server standard config-server
