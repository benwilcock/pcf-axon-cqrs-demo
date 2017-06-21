#!/usr/bin/env bash

cf create-service p-mysql 512mb eventStore
cf create-service p-mysql 512mb queryView
cf create-service p-rabbitmq standard rabbit
#cf create-service p-redis shared-vm redis
cf create-service p-service-registry standard registry
cf create-service p-config-server standard config -c config-server-setup.json
