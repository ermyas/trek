

# Build the docker images

We're using a multiproject structure, so from the root directory
or passing the build.sbt in the root, use sbt to build all the images.

    sbt "project thriftInterfaces" compile
    sbt "project puzzleService" docker
    sbt "project playerService" docker
    sbt "project journeyService" docker
    sbt "project puzzleMasterService" docker

The tests for some of these require a local couchdb server to be running.

# Docker-machine

I'm using `docker-machine` which still has a few issues but apparently it is to be preferred to boot2docker.
Also note cask is now an official part of brew ([https://github.com/caskroom/homebrew-cask#important-december-update-homebrew-cask-will-now-be-kept-up-to-date-together-with-homebrew-see-15381-for-details-if-you-havent-yet-run-brew-uninstall---force-brew-cask-brew-update-to-switch-to-the-new-system](see note here)).

    brew cask install virtualbox
    brew install docker docker-machine docker-swarm docker-compose

    docker-machine create -d virtualbox trek
    docker-machine start trek
    eval $(docker-machine env trek)

# Docker compose

Components can learn the address of other components either by bringing them up
in a single `docker-compose` step, using the `external-links` properties in
the docker compose or through zookeeper.

If you don't plan to use ZK, you can just run the following once you've build all the images
to launch and link all the containers:

    docker-compose -f docker/puzzle_master_service_compose.yml up

# The web-client

To actually play you can use the demonstration web client.
There is a `run` script there that starts a simple http server.


# Preloading some data

At the moment you can't play any puzzle until you've put something in the puzzle store.
You might also need to insert a user into the playerstore, and record the id.

We will add a script to insert a demo user and demo puzzle.

# Zookeeper

## Specifying a zookeeper address

The address can be provided as `zk!host:port!/path/to/service!0`.
This is what you would use as an argument for the `-zk` option
when launching a server.

