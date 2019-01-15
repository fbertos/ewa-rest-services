Commands:

SUBSCRIPTION
curl -i -X POST -H "Content-Type:application/json" -d "{ \"email\":\"fbertos@linalis.com\", \"fullName\":\"Fernando Bertos Matilla\", \"password\":\"fe0be0ma\" }" http://localhost:8000/ewa/subscription

SESSION
curl -i -X PUT -d "username=fbertos@linalis.com" -d "password=fe0be0ma" "http://localhost:8000/ewa/session"
curl -i -X GET http://localhost:8000/ewa/session/5c3dff67931b151a57790edc

CONTACTS
curl -i -X GET -H "Authorization:5c3dff67931b151a57790edc" "http://localhost:8000/ewa/contact?q=fbertos&order=fullName&direction=ASC"