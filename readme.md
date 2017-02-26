# Authentication Service
*By Stevie Amirault*

A working login backend service with
* Authentication
* User creation

Built upon:
* Java
* Spring
* Gradle
* Heroku
* Postgres

## Security Measures
1. Prevents SQL injections via prepared statements
2. Salts and hashes passwords via BCrypt
3. SSL (part of Heroku)
4. Password policy implemented

## How to Use

Base URL: https://amirault-auth-service.herokuapp.com/

1. /create (POST)
	1) A REST POST call for creating a user
	2) Requires two fields:
		1) email - an email address (will be validated against: org.apache.commons.validator.routines.EmailValidator)
		2) password - a password that meets the following requirements:
			1) a digit must occur at least once
			2) a lower case letter must occur at least once
			3) an upper case letter must occur at least once
			4) a special character must occur at least once
			5) no whitespace allowed in the entire string
			6) at least 8 characters
	3) Replies with the following HTTP Status Codes:
		i) 201 - Created! The submitted email and password were added to the database
		ii) 400 - Creation failed! Invalid email or password
	4) On success, users will be added to the database
	
2. /auth (POST)
	1) A REST POST call for authenticating a user
	2) Requires two fields:
		1) email - a non-null string that should be an email address
		2) password - a non-null string
	3) Replies with the following HTTP Status Codes:
		1) 200 - Success! The submitted email was found with the correct password and is now success
		2) 403 - Authentication failed! Invalid email or password
3. /health (GET)
	1) A REST GET call for poking the server to see if it's awake
	2) Requires no parameters
	3) Replies with a friendly message about being alive
	
## Test users

* **Email**: stevie2@amirault.org **Password**: c@T45678
* **Email**: stevie3@amirault.org **Password**: c@T45678
	