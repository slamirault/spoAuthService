# Authentication Service

# Security Measures
1. Prevents SQL injections via prepared statements
2. Salts and hashes passwords via BCrypt
3. SSL
4. Password policy implemented

# How to Use

Base URL: https://amirault-auth-service.herokuapp.com/

1. /create (POST)
	a) A REST POST call for creating a user
	b) Requires two fields:
		i) email - an email address (will be validated against: org.apache.commons.validator.routines.EmailValidator)
		ii) password - a password that meets the following requirements:
			1) a digit must occur at least once
			2) a lower case letter must occur at least once
			3) an upper case letter must occur at least once
			4) a special character must occur at least once
			5) no whitespace allowed in the entire string
			6) at least 8 characters
	c) Replies with the following HTTP Status Codes:
		i) 201 - Created! The submitted email and password were added to the database
		ii) 400 - Creation failed! Invalid email or password
	d) On success, users will be added to the database
	
2. /auth (POST)
	a) A REST POST call for authenticating a user
	b) Requires two fields:
		i) email - a non-null string that should be an email address
		ii) password - a non-null string
	c) Replies with the following HTTP Status Codes:
		i) 200 - Success! The submitted email was found with the correct password and is now success
		ii) 403 - Authentication failed! Invalid email or password