#!/usr/bin/env bash

# Delete the apps
cf delete dehli-accounts -f
cf delete dehli-kotlin -f
cf delete dehli-transactions -f

# Delete the services
cf delete-service accounts-mysql -f
cf delete-service transactions-mysql -f
cf delete-service events-queue -f
cf delete-service config-server -f
cf delete-service shared-redis -f
