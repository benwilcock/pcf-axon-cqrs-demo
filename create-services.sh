#!/usr/bin/env bash
cf create-service p-mysql 512mb mysql
cf create-service p-rabbitmq standard rabbit
