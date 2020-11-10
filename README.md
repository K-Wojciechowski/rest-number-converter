REST Number Converter
=====================

A simple REST service that can convert decimal numbers to hexadecimal or roman numerals.

License: MIT.

API docs
========

The service offers one route (via GET): `/convert/{to}/{number}`.
Use `hex` or `roman` as the `to` parameter. The `number` parameter supports the same numbers Java's `long` supports, i.e. -2^63 ... 2^63-1 for `hex`, and 1 ... 3999 for `roman`. `hex` represents negative numbers in two's complement.

The service will return either the converted number in a 200 response, or an error (text description, error code).

Additionally, going to `/` will show the above docs.

Usage examples
--------------

    $ curl http://localhost:8080/convert/hex/1234
    4d2
    $ curl http://localhost:8080/convert/hex/-1234
    fffffffffffffb2e
    $ curl http://localhost:8080/convert/roman/1234
    MCCXXXIV
    $ curl http://localhost:8080/convert/roman/4321
    Number must be between 1 and 3999 (got 4321)
    $ curl http://localhost:8080/convert/hello/4321
    Unknown operation hello


Running
=======

To run the service: `mvn spring-boot:run` (or `./mvnw spring-boot:run` to use Maven Wrapper, or `./RUN.sh`)  
To run the tests: `mvn verify` (or `./mvnw verify`, or `./TEST.sh`)

The server will be started on localhost:8080 by default. If this is not desirable, this can be changed in `src/main/resources/application.properties`.

Extensibility
=============

Adding a new number system requires modifying the `convert` method of `NumberConverterService` to make it aware of the new method. Adding new (non-standard) Roman numerals (to extend the supported number range) requires adding those to ROMAN_NUMERALS in that class, and adjusting the range check in `toRoman`.
