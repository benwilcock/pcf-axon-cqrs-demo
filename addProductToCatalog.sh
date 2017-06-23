#!/usr/bin/env bash
#export UUID=`uuid`
export UUID=`uuidgen`
export cmdURL="https://pcf-axon-cqrs-demo-command-side.cfapps.io/"
export qryURL="https://pcf-axon-cqrs-demo-query-side.cfapps.io/"
echo "CmdURL= ${cmdURL}"
echo "QryURL= ${qryURL}"
echo "Generated ProductID= ${UUID}"
curl -H "Content-Type:application/json" -d "{\"id\":\"${UUID}\", \"name\":\"test-${UUID}\"}" ${cmdURL}/add
curl -s ${qryURL}/products | grep test-${UUID}