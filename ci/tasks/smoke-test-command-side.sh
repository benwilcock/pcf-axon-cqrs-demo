#!/usr/bin/env bash

apt-get update && apt-get install -y curl --allow-unauthenticated

set -ex

if [ -z $URL ]; then
  echo "The URL to test has not been set."
  exit 1
fi

# Make sure the homepage shows...

if curl -sL -w %{http_code} "$URL" -o /dev/null | grep "200"
then
    echo "The website [$URL] shows 'HTTP/1.1 200 OK' (as expected)."
else
    echo "Error. Not showing '200 OK' on [$URL]"
    exit 1
fi

# Make sure the /info endpoint shows...

if curl -sL -w %{http_code} "$URL/info" -o /dev/null | grep "200"
then
    echo "The website [$URL/info] shows 'HTTP Status 200 OK' (as expected)."
else
    echo "Error. Not showing '200 OK' on [$URL/info]"
    exit 1
fi

# Make sure the /env endpoint shows...

if curl -sL -w %{http_code} "$URL/env" -o /dev/null | grep "200"
then
    echo "The website [$URL/env] shows 'HTTP Status 200 OK' (as expected)."
else
    echo "Error. Not showing '200 OK' on [$URL/env]"
    exit 1
fi

# Make sure the homepage shows there is a DataBase Service bound...

if curl -s "$URL" | grep "MySQL"
then
    echo "The website [$URL] shows 'MySQL' (as expected)."
else
    echo "Error. Not showing 'MySQL' on [$URL]"
    exit 1
fi

# Make sure the homepage shows there is a Messaging Service bound...

if curl -s "$URL" | grep "Rabbit MQ"
then
    echo "The website [$URL] shows 'Rabbit MQ' (as expected)."
else
    echo "Error. Not showing 'Rabbit MQ' on [$URL]"
    exit 1
fi

exit 0