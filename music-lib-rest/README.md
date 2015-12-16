# PA165 Music Library - REST module guide

## Install

Run server by command:

> mvn tomcat7:run

**Note:** If your default port (8080) is occupied e.g. by web server you can run rest module in different port by using

> mvn -Dmaven.tomcat.port=XXXX tomcat7:run  (where XXXX is port)

## Album entity##

Below is examples of rest requests.
**Please change port in commands accordingly to your server**

###Get albums###

**API URL**

> /pa165/rest/albums

**Example**

> curl -i -X GET http://localhost:8080/pa165/rest/albums

###Get albums by title###

**API URL**

> /pa165/rest/albums?title={title}

**Example**

> curl -i -X GET http://localhost:8080/pa165/rest/albums?title=Jukebox

###Create album###

**API URL**

> /pa165/rest/albums/create

**Example**

> curl -i -X POST -H "Content-Type: application/json" --data '{"title":"Nazov albumu","commentary":"Komentar k albumu"}' http://localhost:8080/pa165/rest/albums/create

### Get album by id###

**API URL**

> /pa165/rest/albums/{id}

**Example**

> curl -i -X GET http://localhost:8080/pa165/rest/albums/1

###Update album###

**API URL***

> /pa165/rest/albums/{id}

**Example**

curl -i -X PUT -H "Content-Type: application/json" --data '{"title":"Zmena nazvu","commentary":"Iny komentar k albumu"}' http://localhost:8080/pa165/rest/albums/4

###Delete album###

**API URL**

> /pa165/rest/albums/{id}

**Example**

curl -i -X DELETE http://localhost:8080/pa165/rest/albums/4