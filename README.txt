Commands:

CREATE
curl -i -X POST -H "Content-Type:application/json" -d "{ \"email\":\"fbertos@linalis.com\", \"fullName\":\"Fernando Bertos Matilla\", \"password\":\"fe0be0ma\" }" http://localhost:8000/ewa/subscription

SESSION

curl -X PUT -d "username=fbertos@linalis.com" -d "password=fe0be0ma" http://localhost:8000/ewa/session
