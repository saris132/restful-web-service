# restful-web-service

This Project has an implementation of resources with multiple features like versioning, basic authentication (Spring Security), documentation (Swagger), exception handling, filtering and hateoas. Other features are:
-	Use auto configuration, spring initializr and starter projects.
-	Connection of web services to a database using jpa and hibernate.
-	Implement exception handling, validation, hateoas and filtering for restful web services.
-	Use a wide variety of spring boot starter projects - spring boot web, spring boot web services, spring boot data jpa.
-	Can version, monitor (spring boot actuator) and document (swagger).

This are the request that can be made in Postman:

**Get users (GET)**   
localhost:8080/jpa/users

**Retrieve one user (GET)**  
localhost:8080/jpa/users/{id}  
localhost:8080/jpa/users/189

**Create user (POST)**  
  localhost:8080/jpa/users  
  Send in Body-raw-json  
    {  
      "name": "Joe",  
      "birthDate": "2021-12-10T05:00:00.000+00:00",  
      "posts": []  
    }       

**Delete user (DELETE)**  
  localhost:8080/jpa/users/{id}  
  localhost:8080/jpa/users/1003

**Get user posts (GET)**  
  localhost:8080/jpa/users/{id}/posts  
  localhost:8080/jpa/users/1001/posts

**Create post (POST)**  
  localhost:8080/jpa/users/{id}/posts  
  localhost:8080/jpa/users/1001/posts  
  Send in Body-row-json  
    {  
      "id": 11001,  
      "description": "My first post"  
    }     


Finally, you can use Swagger this way:

The project was developt using Eclipse EE.
