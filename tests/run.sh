#!/bin/bash

cd ../n2nir
mvn clean install
cd ..
cd tests
./bats/bin/bats n2nir.bats
