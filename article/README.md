# Building Reactive Microservices with Scala, Finagle and Thrift on Bluemix

## Abstract

A number of recent industry trends are enabling the creation of more scalable,
higher performance systems that are easier to develop, maintain and deploy.
Microservices, reactive architectures, concurrency abstractions, and the rise
of functional programming on the JVM are some of the trends that this article
touches upon. 

Microservice architectures are gaining popularity over monolithic applications.
The premise of this approach is that developing small, single-function services
simplifies the deployment, scalability, and extensibility of a system. However,
this approach places additional importance on optimising communication between
services (API management) and efficiently composing their execution.

Apache Thrift is a service framework that offers an Interface Definition
Language (IDL) and a code generator for creating consistent service APIs and
client libraries supporting multiple programming languages and optimized call
serialization. Finagle, is a protocol-agnostic Scala Remote Procedure Call
(RPC) framework, which provides asynchronous service communication through
easily composable concurrency abstractions. This article discusses how these
two frameworks can be used together to implement microservices in Scala.
Specifically, the objectives of this article is to: 1) introduce the concepts
listed above in greater detail; 2) give a clear practical example of the use of
these approaches; and 3) demonstrate how such a system can be deployed on
Bluemix, a Platform as a Service (PaaS) powered by IBM.


## Introduction

*Provides a brief introduction of the various concepts that will be discussed,
and outlines the structure and objectives of the article.*

## Example application

*Provide an overall description of the application that will be used in this
article, and list out the core features that will be relevant to the
discussions presented. The source of the example application will be available
on github.com.*


To demonstrate the concepts discussed in this article we have developed an
example application, the full source of which can be found on github.

Trek is a virtual scavenger hunt game where a player must find the location on
a map that corresponds to a given clue. The game supports multiple puzzles,
each of which contains multiple clues.

The game has four core services: puzzle, player, journey and master. The first
three store data in respective databases, while the master provides a single
interface to users.


## Implementation

For simplicity we use make the same technology choices across all backend
services: couchdb for persistence and Scala as our language. This is by no
means necessary and in fact one of the benefits of microservice architectures
is the flexibility we gain in the use of technologies for each service. If the
functionality of one component is better suited to a particular language or
database, then we are free to make that choice for that service.

We also provide a browser-based front-end implementation for playing the game.
This front-end sends requests to the master service to retrieve puzzles and
record the player's progress.

## Microservices

Overview: provide a brief introduction to microservices, in contrast to
monolithic architectures.


Monolithic architecture of the example application: present a monolithic
architecture for the demo application and discuss some of the challenges by
presenting more concrete examples. 

Microservice architecture of the example application: decompose the monolithic
architecture into microservices. Discuss the effects of the evolution (changes
to the API, improvements to scalability, ease of deployment, reusability, and
extensibility).

## Technologies

### Thrift

 - Introduction: discuss the rationale behind Thrift, briefly contrast to
   alternative approaches (XML RPC, Protocol Buffer, GRPC, etc.). 

 - IDL and code generation: use the microservices of the example application to
   explain Thrift IDL, and code generation for Scala and Javascript.

### Finagle

 - Introduction: discuss the rationale behind Finagle.

 - Concurrency in Finagle: discuss concurrency in Finagle by showing code
   snippets of how it is used to compose asynchronous microservice calls and
   aggregate and filter results.

 - Admin interface: provide a brief overview of the inbuilt admin interface
   created by Finagle, for the microservices.

### Cloudant

 - Introduction: brief overview of Cloudant and CouchDB. 

 - CouchDb Scala client library: provide an overview of the library used in the
   example application to persist and query data in Cloudant.

### Docker

 - Introduction: plenty has been written on this, we only note here that we
   have used docker.

### Deployment to Bluemix

 - scala buildpack


## Defining a service with Thrift

Let's take a look at a Thrift definition for the `PuzzleMasterService`.
The syntax borrows from Java and C.
This service will handle requests from the gameplay client.


```C
service PuzzleMasterService {
    Puzzle startPuzzle( string playerId, string puzzleId );
    list<Puzzle> getPuzzleList ( i32 limit, i32 skip );
}
```

We have here a service called `PuzzleMasterService` with two method signatures:
`startPuzzle` and `getPuzzleList`. This is similar to an interface definition
in Java; it tells us what methods we must implement when providing this service
and it tells clients what methods are available and what their arguments and
return types are.

Thrift offers a few base types (e.g., string and i32) and the ability to define
your own using `Struct`s. `i32` is a 32-bit signed integer. Here we have used a
custom `Puzzle` struct which is defined as follows:

```C
struct Puzzle {
  1: optional Model.PuzzleId id,
  2: list<PuzzleSite> trail,
  3: string startMessage,
  4: string endMessage,
  5: Model.PlayerId owner,
  6: optional Model.Coordinate startCoord,
  7: optional i32 startZoom
}
```

This `struct` definition introduces a few more features of Thrift. First, the
members are numbered (a field identifier). This is to help with versioning. It
is not strictly necessary (the compiler will add identifiers if they're absent)
but it is good practice. The field identifier and the type are used to uniquely
identify the field.

Fields marked `optional` will not be serialized if they have not been set.
The practical implications of this differ between languages as we shall see below.

The type of the `id` field is `Model.PuzzleId`. This is actually just
a `typedef` to ensure consistency across service definitions.
We define a number of these aliases in a file `Model.thrift` that will
be included in various service definitions. The *name* of the file without
the extension becomes the namespace. Thus, in `Model.thrift` you will find
the following definition:

```C
typedef string PuzzleId
```

 You will see a `trail` is a list of `PuzzleSite`s. There are two other
*container types*, namely `set` and `map`. These correspond to standard
containers when code is generated.

## Implementing a service in Scala

1. Generate the Thrift source files
2. Implement the service
3. Stand up the service

```Scala
class PuzzleMasterServiceImpl(
                               playerClient: PlayerService.FutureIface,
                               puzzleClient: PuzzleService.FutureIface,
                               timestampGenerator: TimeStampGenerator
                             ) extends PuzzleMasterService.FutureIface {


  override def startPuzzle(playerId: String, puzzleId: String) = 
    puzzleClient.get(puzzleId)

  override def getPuzzleList(limit: Option[Int], skip: Option[Int]): Future[Seq[Puzzle]] = {
    (limit, skip) match {
      case (Some(n), Some(k)) => puzzleClient.getAll(n, k)
      case (Some(n), None) => puzzleClient.getAll(n, 0)
      case _ => puzzleClient.getAll(10, 0)
    }
  }
}
```

## Accessing a service in Javascript

1. Generate the Thrift source files
2. Include them as resources
3. Issue remote procedure calls via the client



