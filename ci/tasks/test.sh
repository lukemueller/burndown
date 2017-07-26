#!/usr/bin/env bash

set -xe

pushd source-code
./gradlew clean test
popd