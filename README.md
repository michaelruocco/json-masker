# JSON Masker

[![Build](https://github.com/michaelruocco/json-masker/workflows/build/badge.svg)](https://github.com/michaelruocco/json-masker/actions)
[![codecov](https://codecov.io/gh/michaelruocco/json-masker/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/json-masker)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/6890f592d8084b6e91e15d1788211c57)](https://www.codacy.com/gh/michaelruocco/json-masker/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/json-masker&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/json-masker?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_json-masker&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_json-masker)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_json-masker&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_json-masker)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_json-masker&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_json-masker)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_json-masker&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_json-masker)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco/json-masker.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco%22%20AND%20a:%22json-masker%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Overview

This library allows the masking of specific values within a JSON payload by specifying JSON path configurations

It uses a combination of [Jackson](https://github.com/FasterXML/jackson) and
[JsonPath](https://github.com/json-path/JsonPath) to achieve this. Hopefully the tests, particularly the integration
tests, give some examples of how the library should be used, but some additional explanation is below.

### Setup

The first step is to create an instance of the JsonMasker, in order to do this 4 other instances are required:

*   An instance of a Jackson ObjectMapper

*   A Collection of JsonPath objects, these paths should point to the values in your JSON payload that you want to mask

*   An instance of a class implementing the MapFunction interface from the JsonPath library, this is class is
    responsible for applying a transformation to any JSON fields that match the collection of JsonPath objects that
    you provide. There is a default implementation of this class that is used if you do not provide one which is called
    MaskFunction this will simply mask the entire value using asterisks.
    
*   An instance of the Configuration class that also comes from the JsonPath library, this provides configuration
    options that JsonPath uses when parsing your json payload. There is a default set of options provided if you do not
    provide one specifically which is created by the JsonPathConfig factory class. By default it will use Jackson as the
    JSON provider and will suppress any exceptions, so as an example the masker will not error if the JsonPath you have
    provided does not find a match in the input JSON payload. Of course because this can be passed in you can set up any
    different configuration options you wish to.

As an example, if you wanted to use the default MapFunction and Configuration classes described above, you can set up an
instance of the JsonMasker as follows:

```java
ObjectMapper mapper = new ObjectMapper();
Collection<String> paths = Arrays.asList("$.myField1", "$.myField3");
JsonMasker myMasker = JsonMasker.builder()
    .mapper(mapper)
    .paths(JsonPathFactory.toJsonPaths(paths))
    .build();
```

This example just passes a simple new instance of ObjectMapper with no customizations, you can set it up however you
need to before passing in to the masker if required. You can set up your paths as a simple Collection of Strings and
then use the JsonPathFactory to convert those into the JsonPath objects that are required, or you can build them
yourself if you prefer.

### Performing Masking

Once you have an instance of the masker set up as above, all you need to do is call the apply method passing in
the json payload you want to mask. e.g.

```
String payload = "{\"myField1\":\"mask me\"}";
String maskedPayload = myMasker.apply(payload);
```

JsonMasker implements the UnaryOperator<String> interface which means you can use it when processing java Streams if
required. The example above is configured to mask two fields in a JSON payload named myField1 and myField3.
If the masker were to be passed the following json payload:

```json
{
  "myField1": "mask me",
  "myField2": "another value",
  "myField3": 123
}
```

Then it would return the following:

```
{
  "myField1": "*******",
  "myField2": "another value",
  "myField3": "***"
}
```

So it will replace all characters with an asterisk. It is worth noting that it has turned the last numeric field into a
string to ensure that it still returns valid JSON.

### Customising

As mentioned above it is possible to provide your own MapFunction, so you can customize the way the fields are masked,
or if you wish, apply some completely different type of transformation on the values. The MapFunction itself requires
another lower level object called an ObjectMasker which in turn requires an instance of the UnaryOperator<String>
interface, this is what actually does the work of applying the masking / transform to each value. There are currently
three implementations available by default:

*   StringMasker, which performs the simple replacement of all chars with an asterisk as shown above, this is what you
    get by default if you don't provide one specifically
    
*   IgnoreLastNCharsMasker, this is the same as above except will ignore the last N characters, if you provide 3 as the
    lastN value it would take the string "my-value" and return "*****lue"

*   IgnoreFirstNAndLastNCharsMasker, as the name suggests this is similar to IgnoreLastNCharsMasker except it also
    ignores the first N chars too, so if you provide 2 as the firstN value and 3 as the lastN value it would take the
    string "my-value" and return "my***lue"

As these are all implementing an interface, you are free to provide a custom implementation if you wish to. As a very
simple example, lets say you wanted to mask your string but leave the last 3 characters in the clear, and you wanted to
use a hyphen instead of an asterisk as the mask character, you could set your object mapper up like this:

```java
ObjectMapper mapper = new ObjectMapper();
Collection<String> paths = Arrays.asList("$.myField1", "$.myField3");
return JsonMasker.builder()
        .maskFunction(new MaskFunction(new ObjectMasker(new IgnoreLastNCharsMasker(3, '-'))))
        .paths(JsonPathFactory.toJsonPaths(paths))
        .mapper(MAPPER);
```

As this is just a noddy example I have just created instances of each class directly, if you have commonly used classes
then you can extend any of these classes and customise them as you wish, if you write something you find particularly
useful you could even consider contributing it back to into this library but again that is up to you.

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// formats code
// builds code
// runs tests
// runs integration tests
// checks for gradle issues
// checks dependency versions
./gradlew clean currentVersion dependencyUpdates spotlessApply lintGradle build integrationTest
```