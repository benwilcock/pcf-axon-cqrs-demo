#!/bin/bash

set -ex

pushd pcf-springboot-demo-source
  echo "Fetching Dependencies & Building Code..."
  ./gradlew assemble > /dev/null

  echo "Running Tests..."
  ./gradlew test
popd

exit 0
