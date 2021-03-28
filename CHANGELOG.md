# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 0.2.0 - 2021-03-28
### Changed
- TestExecutionListener will not longer throw unhandled exception if there is no slf4j or logback in classpath.
If required libraries are missing - `nop` mode is activated.

## [0.1.0] - 2020-08-30
### Added
- TestExecutionListener that discards all log events related to passing tests

[0.1.0]: https://github.com/zeldigas/Reasonable-Test-Logs/tree/v0.1.0