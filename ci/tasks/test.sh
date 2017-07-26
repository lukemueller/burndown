#!/usr/bin/env bash

pushd source-code
./gradlew clean test
popd