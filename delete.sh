#!/usr/bin/env bash

# Delete the apps
cf delete accounts -f
cf delete transactions -f

# Delete the services
cf delete-service accounts-mysql -f
cf delete-service accounts-redis -f
cf delete-service transactions-mysql -f
cf delete-service transactions-redis -f
cf delete-service events-queue -f
cf delete-service config-server -f
