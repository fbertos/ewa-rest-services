Commands:

CREATE
curl -i -X POST -H "Authorization:3423fdsfddd432434dffs20018!!" -H "Content-Type:application/json" -d "{ \"name\":\"Fernando Bertos Matilla\" }" http://localhost:9090/create

CREATE WITH FILE
curl -i -X POST -H "Authorization:3423fdsfddd432434dffs20018!!" -F "user={ \"name\":\"Fernando Bertos Matilla\" };type=application/json" -F "file=@/eula.1028.txt;type=text/plain" http://localhost:8090/file

JVM=-Dserver.port=8090 to change the port


READ
curl -i -X GET -H "Authorization:3423fdsfddd432434dffs20018!!" http://localhost:9090/read
