# Reasonable Test Logs

Junit5 test execution listener that hides log events for "green" tests.

## Rationale

Have you ever faced with the logging noise during your project build? Maybe you had opportunity to download tens 
megabytes of log to analyze issue just because DEBUG level was enabled on some chatty package "in case of something goes wrong"?
Big chance you did, if your project uses some logging framework (like slf4j), and you write unit tests. 

The reason is simple - logs allow you to get extra information if something goes wrong, 
so there is no much sense in muting logs during tests run. At the same time we need logs, as a source of information, if
test fails and here's come the problem - there is no out of the box integration between JUnit and logging system that
can discard all logged events when test passes and send them to configured appenders if something goes wrong.

This project aims to solve this problem providing this missing piece.

## Usage

If your codebase uses supported frameworks (see prerequisites [below](#prerequisites)), just add test dependency:
```xml
<dependency>
      <groupId>com.github.zeldigas</groupId>
      <artifactId>reasonable-test-logs</artifactId>
      <version>0.2.0</version>
      <scope>test</scope>
</dependency>
```

### Prerequisites

#### Test engines
This project is JUnit5 only, although if you have use JUnit5 as a base and have some JUnit4 tests executed via vintage
engine, it will work as well.

#### Logging frameworks
Right now only Logback is supported, although it should be possible to add support for other frameworks as well (e.g. log4j2)
in case of need and request from community.

### Configuration
Default behavior for listener is to work only when running via build system (e.g. maven) execution and do nothing with logs
when executed from IDE's run-test option. This provides reasonable balance between log amount and usability - IDE is usually 
smart enough to provide good navigation in logs and show only relevant info.

This can be adjusted with defining configuration properties in the following ways (in the order of priority):
1. `reasonable-test-logs.properties` in root of classpath
2. System properties (one specified with `-D` key)
3. Environment variables

| Property name | Env variable format | Description |
|---------------|---------------------|-------------|
| `rtlogs.mode` | `RTLOGS_MODE` | Type of listener to use. Possible options: <br/> `auto` -  "default" mode that disables logic in IDE and enables in regular build system run. <br/> `nop` - explicit NOP mode (IDE mode) <br/> `reasonable` - explicit logs processing mode (always build system) |
| `rtlogs.debug` | `RTLOGS_DEBUG` | Enables printing of debug messages in listener |    
| `rtlogs.disable-spring-boot` | `RTLOGS_DISABLE_SPRING` | Controls if SpringBoot logging reconfigurer code is suppressed or not. `true` by default |    

### Limitations
It is assumed that tests are executed in sequential manner. Parallel test execution support was not researched so far. 

## Examples

Check `example` directory for various test-samples. The simplest way to run it -- execute the following command from repo root:
```
mvn clean package
```
Take a look at an amount of logs and then compare it to the mode when listener is disabled:
```
mvn clean package -Drtlogs.mode=nop
```

## Roadmap

- `0.1.0` - add test coverage for listener code, publish on maven central   

## Acknowledgments
As a lot of things in our world, this project is not something unique. Once idea come to my mind, 
I've googled and found out that [some folks](https://www.novatec-gmbh.de/en/blog/suppressing-logs-successful-tests/)
implemented similar ideas using JUnit4 test extension mechanism.

While it works, I found it harder to use than I want, because you need to configure such places manually or setup "catch-all" extension
in build system (e.g. surefire params for junit5).

Anyway, some techniques published on [github](https://github.com/nt-ca-aqe/testit-testutils/tree/master/testutils-logsuppressor-logback)
were useful.

# Development

## Release

1. Make sure your `~/.m2/settings.xml` contains
  - profile with property `gpg.keyname` valid gpg key id
  - server definition with id `ossrh` with valid credentials to nexus oss repo
2. Upload of artifacts is as simple as:
```bash
cd reasonable-test-logs
mvn clean deploy -Prelease
```

  
