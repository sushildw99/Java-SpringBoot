
### Introduction
This is a SpringBoot Project and Source Code is availabe at numgen/src, Junit and Integration tests are available at - numgen/src/test/java/com/sushil/numgen. There are 3 Rest API, Description and CURL Commands are as follows, These CURL commands are executed successfully in MacOS Mojave. This is a Maven Project and I have run it through IntelliJ IDEA by running the main Class - com.sushil.numgen.NumgenApplication

**Rest Controller:** com.sushil.numgen.controller.NumgenController


Junit and Integration Test are succussfully executed and availabe at: numgen/src/test/java and same can be run through IntelliJ IDEA

 1. POST http://localhost:8080/api/generate
     
         Payload: {"Goal":"10","Step":"2"}

	It returns a TASK_ID(UUID ) with status code '202 Accepted' and asynchronously creates a file at location - /tmp/{TASK_ID}_output.txt and write the numbers in decreasing order as given in Goal in request payload with difference as a Step. If Goal="10" and Step="2" then following numbers will be written in this file:

	    10,8,6,4,2,0
	 
	So if TASK_ID=811e7f1e-4191-471a-9b42-769cfb7a7850 then file will be created with following name and location,
	    /tmp/811e7f1e-4191-471a-9b42-769cfb7a7850_output.txt

	**Request:**

	    curl -H "Content-Type: application/json" -X POST -d '{"Goal":"10","Step":"2"}' http://localhost:8080/api/generate

	**Response:**

	    {"task":"811e7f1e-4191-471a-9b42-769cfb7a7850"}

 2. GET
    http://localhost:8080/api/tasks/811e7f1e-4191-471a-9b42-769cfb7a7850/status
	
	It return the status of TASK_ID as given in path param. Status can be SUCCESS, IN_PROGRESS, ERROR
	
	**Request:**

	    curl -H "Content-Type: application/json" -X GET http://localhost:8080/api/tasks/811e7f1e-4191-471a-9b42-769cfb7a7850/status

	**Response:**

	    {"result":"SUCCESS"}

 3. GET
    http://localhost:8080/api/tasks/811e7f1e-4191-471a-9b42-769cfb7a7850?action=get_numlist
    
    It returns all the numbers written in the file as in below response.

	**Request:**
	

        curl -H "Content-Type: application/json" -X GET http://localhost:8080/api/tasks/811e7f1e-4191-471a-9b42-769cfb7a7850?action=get_numlist


    
	**Response:**
	
	    {"result":"10,8,6,4,2,0"}
    




