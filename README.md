# Syslog Analyzer Using Scala AKKA Framework

### Problem Statement

Create a Linux system log (/var/log/messages) analyzer base on REST service.

The user enters 3 fields: the date from which the data is analyzed (datetimeFrom), the date until which the data is analyzed (datetimeUntil), 
the searchable phrase (phrase). The service must analyze the log file (/var/log/messages) 
and select the records that are in the period from (datetimeFrom) to (datetimeUntil) 
and the phrase (phrase) is found in the record.

### Solution Summary

After starting, this application takes a syslog(demo file added in the repo) file name from `application.config` file. 
Then parse the syslog file, separate each line and lastly divide each line into two parts: 

1. datetime
2. message

After parsing, load data into h2 database. It provides 4 rest endpoint

|Description|HTTP Method|URI|Request|Response|
|-----------|-----------|---|-------|--------|
|Get system status| GET | /api/get_status|N/A|```{"status": "ok"}```|
|Get total log size| GET| /api/get_size|N/A|```{"size": 12000}```|
|Get data|POST| /api/data|```{"datetimeFrom": 1619287644857,"datetimeUntil": 1619291220000,"phrase": "systemd-networkd"}```|```{"data":[{"datetime":1619289169000,"message":"ucchwas systemd[1]: Starting Dispatcher daemon for systemd-networkd...","highlightText":[{"fromPosition":52,"toPosition":67}]},{"datetime":1619289170000,"message":"ucchwas networkd-dispatcher[1395]: WARNING: systemd-networkd is not running, output will be incomplete.","highlightText":[{"fromPosition":45,"toPosition":60}]},{"datetime":1619289170000,"message":"ucchwas systemd[1]: Started Dispatcher daemon for systemd-networkd.","highlightText":[{"fromPosition":51,"toPosition":66}]}],"datetimeFrom":1619287644857,"datetimeUntil":1619291220000,"phrase":"systemd-networkd"}```|
|Get histogram|POST|/api/histogram|```{"datetimeFrom": 1619287644857,"datetimeUntil": 1619291220000,"phrase": "systemd-networkd"}```|```{"histogram":[{"datetime":1619289169000,"counts":1},{"datetime":1619289170000,"counts":2}],"datetimeFrom":1619287644857,"datetimeUntil":1619291220000,"phrase":"systemd-networkd"}```|


### Technology Used

- Language: Scala
- Framework: Akka
- Library: Slick(database query and access library), Jackson(JSON processor), Flyway(Version control for database)
- Build Tool: SBT

Note: Intellij IDE is used for this project

### Project setup 

Import project in your IDE, it will download all dependencies. 

### Run

Go to > src/scala/Main.scala > right-click > run 'Main'

`default url: http://127.0.0.1:8080/get_status`

### Learning 

- file import and parsing in scala
- database operation in scala
- raw query in scala
- typesafe query using slick
- functional programming
- Future, Seq, List, Req 
- h2 database integration and operation
- rest api using scala 
- api routing in scala

### Limitations:
- Need to add unit testing
- Need to add complete logging system
- Need to add data validation and exception handling
- Support only linux syslog file in this stage








