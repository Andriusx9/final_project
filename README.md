
# Final Project - INVOICE SYSTEM

final project


## Installation

The project is created with Maven, so you just need to import it
 to your IDE and build the project to resolve the dependencies
    
## Database Configuration

It uses a H2 in-memory database 
 can be changed easily in the application.properties for any other database.
Add to 

``
  /resources/application.properties
``

```bash
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```

## Database diagram

![App Screenshot](https://i.postimg.cc/xd9jLMVX/DB-Geras.png)
