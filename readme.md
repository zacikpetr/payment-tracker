# Payment Tracker

This application can track Currency-Value mappings and store it into in-memory database.
List of values is printed out into console once per minute until user quits this application.
User can preload Payment values from file or add them manually by typing them directly into console.

This project represents simple, clean and effective solution.

## Prerequisities
Java 8 is needed to build and run application. Gradle is not required as it is provided embedded in application as wrapper.

## Build
Application can be built using Gradle wrapper. Run this command under project root directory.

```
./gradlew clean build
```
or
```
gradlew.bat clean build
```

This step is responsible for application, building, testing and packaging.

## Run

### Self-executable JAR
Application is packaged into self-executable JAR file. 
To run application, first navigate to `libs` directory:


```
cd build/libs
```

>Note: Application must be built first in order to see all directories.

Execute application by calling:

```
java -jar payment-tracker-1.0-SNAPSHOT.jar 
```

## Usage

`java -jar payment-tracker-1.0-SNAPSHOT.jar [filename.csv]`

First parameter is optional and in can specify CSV filename to preload Payments from.

Example of file structure:
```
EUR 200
USD 400
CZK 1000
```
>Note: Space is used as value delimiter. First value represents Currency and second parameter represents Amount.

- User can interact with application by writing down into console attributes of new Payment. To modify existing payment simply write payment `CZK -100` followed by enter.
This modifies existing payment in `CZK` currency and that makes it `CZK 900` in our example.
- User can quit application any time by writing down `quit` command and pressing enter. All data from current run will be lost.
- Any _unknown_ commands will be reported back to user on `stderr`, for example, if user sends _unknown_ command, application  reports: `Cannot parse input value.`
