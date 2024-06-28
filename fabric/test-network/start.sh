#!/bin/bash

./network.sh down

./network.sh up createChannel -c supplychain -ca -s couchdb

cd addOrg3

./addOrg3.sh up -c supplychain -ca -s couchdb

cd ..

cd addOrg4

./addOrg4.sh up -c supplychain -ca -s couchdb

cd ..

cd addOrg5

./addOrg5.sh up -c supplychain -ca -s couchdb

cd ..

cd addOrg6

./addOrg6.sh up -c supplychain -ca -s couchdb

cd ..

./network.sh deployCC -c supplychain -ccn chaincode -ccp ../chaincode/ -ccl java