#!/usr/bin/env bash

apt-get update && apt-get install -y curl --allow-unauthenticated

#set -ex

if [ -z $cmdURL ];
then
  echo -e "\e[31mThe Command-side URL to test has not been set."
  exit 1
else
  echo -e "The Command-side URL for this smoke test is: \e[32m $cmdURL \e[0m"
fi

if [ -z $qryURL ];
then
  echo -e "\e[31mThe Query-side URL to test has not been set."
  exit 1
else
  echo -e "The Query-side URL for this smoke test is: \e[32m $qryURL \e[0m"
fi

# Make sure the homepage shows...

if curl -sL -w %{http_code} "$cmdURL" -o /dev/null | grep "200"
then
    echo "[$cmdURL] shows 'HTTP/1.1 200 OK' (as expected)."
else
    echo -e "\e[31mError. Command-side unresponsive - not showing '200 OK' on [$cmdURL]"
    exit 1
fi

if curl -sL -w %{http_code} "$qryURL" -o /dev/null | grep "200"
then
    echo "[$qryURL] shows 'HTTP/1.1 200 OK' (as expected)."
else
    echo -e "\e[31mError. Command-side unresponsive - not showing '200 OK' on [$qryURL]"
    exit 1
fi

# Begin the Integration-testing...

export UUID=`uuid`
export PRODUCT_ID=`curl -s -H "Content-Type:application/json" -d "{\"id\":\"${UUID}\", \"name\":\"test-${UUID}\"}" ${cmdURL}/add`

if [ "$PRODUCT_ID" = "$UUID" ]
then
    echo -e "The command [$cmdURL/add] for Product $UUID returned ID $PRODUCT_ID (as expected)."
else
    echo -e "\e[31mError. The Product ID $UUID wasn't returned as expected (got $PRODUCT_ID)! \e[0m"
    exit 1
fi


echo -e "\e[32mINTEGRATION-TESTS COMPLETED - ZERO ERRORS ;D "
exit 0