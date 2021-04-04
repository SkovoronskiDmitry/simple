**Simple web application for managing the employee database**

The application is made using the REST API architectural 
style witch perform CRUD operations on employees.
The application consists of three main layers:

- Rest Controller.

- Service.

- DAO.

**Build project**

1. Create "war" using maven plugin:
```
mvn clean package
```
2. Copy "war" to the webapps/webapp folder of the tomcat server;

3. Startup Tomcat server;

4. The application is available at:
```
http://localhost:8080/webapp
```

**Use swagger**
```
http://localhost:8080/webapp/swagger-ui.html
http://localhost:8080/webapp/v2/api-docs
```