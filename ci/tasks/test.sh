#!/usr/bin/env bash

set -xe

pushd burndown-git
./gradlew clean test
popd