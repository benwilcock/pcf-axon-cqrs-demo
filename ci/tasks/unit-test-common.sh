#!/bin/bash

set -ex

pushd source-code
  echo "Fetching Dependencies & Building Code..."
  ./gradlew assemble > /dev/null

  echo "Running Tests..."
  ./gradlew test
popd

exit 0
