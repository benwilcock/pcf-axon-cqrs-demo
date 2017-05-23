#!/usr/bin/env bash
export UUID=`uuidgen`
curl -H "Content-Type:application/json" -d "{\"id\":\"${UUID}\", \"name\":\"test-${UUID}\"}" ${URL}/add
