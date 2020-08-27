# Reasonable Test Logs

Junit5 test execution listener that hides log events for passing tests.

## Rationale

Have you ever faced with the logging noise during your project build?
Big chance you did, if your project uses some logging framework, like slf4j, and you write unit tests. 

The reason is simple - logs allow you to get extra information if something goes wrong, 
so there is no much sense in muting logs during tests run. At the same time we need logs, as a source of information, if
test fails and here's come the problem - there is no out of the box integration between JUnit and logging system that
can discard all logged events when tests passes and send them to configured appenders if something goes wrong.

This project aims to solve this problem providing the missing piece.

## Usage

If your codebase uses supported frameworks (see prerequisites below), just add test dependency:
```xml
<dependency>
      <groupId>com.github.zeldigas</groupId>
      <artifactId>reasonable-test-logs</artifactId>
      <version>0.1.0</version>
      <scope>test</scope>
</dependency>
```

**NB**: Jar is not available in maven central yet

### Prerequisites

#### Test engines
This project is JUnit5 only although if you have use JUnit5 as a base and have some JUnit4 tests executed via vintage
engine, it will work as well.

#### Logging frameworks
Right now only logback is supported, although it should be possible to add support for other frameworks as well (e.g. log4j2)
in case of need and requests from community.


## Examples

Check `example` directory for various test-samples. Simples way to run it, execute the following command from repo root:
```
mvn clean package
```

## Roadmap

- `0.1.0` - add test coverage for listener code, publish on maven central   

## Acknowledgments
As a lot of things in our world, this project is not something unique. Once idea come to my mind, 
I've googled and found out that [some folks](https://www.novatec-gmbh.de/en/blog/suppressing-logs-successful-tests/)
implemented similar ideas using JUnit4 test extension mechanism.

While it works, I found it harder to use than I want, because you need to configure such places manually or setup "catch-all" extension
in build system (e.g. surefire params for junit5).

Anyway some techniques published on [github](https://github.com/nt-ca-aqe/testit-testutils/tree/master/testutils-logsuppressor-logback)
was useful.
  