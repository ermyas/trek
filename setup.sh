#!/bin/bash

# Install dependencies
#brew install couchdb
#brew cask install virtualbox
#brew install docker docker-machine docker-swarm docker-compose

# Launch couchdb (for running tests) and start virtualbox
#couchdb
#docker-machine create -d virtualbox trek
#docker-machine start trek
eval $(docker-machine env trek)

# Build the docker images
sbt "project puzzleService" docker
sbt "project playerService" docker
sbt "project journeyService" docker
sbt "project puzzleMasterService" docker

# Docker compose
docker-compose -f docker/puzzle_master_service_compose.yml up

# The web-client


# Load some data
# ./scripts/load $DOCKER_MACHINE_IP:$PORT data.json

