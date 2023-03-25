[![Typing SVG](https://readme-typing-svg.herokuapp.com?font=Fira+Code&pause=1000&width=435&lines=ToDo+application)](https://git.io/typing-svg)
<h2><a>Web application for storing and managing tasks</a></h2>

After authorization/registration, the user enters an individual page

* Created tasks are displayed on the main page of the user
* Each task can be modified, change status and priority
* You can add new tasks and delete created ones
* Tasks have tags

<h3 ><a>Deployed app</a></h3>

<h3><a>Building: </a></h3>

```---```

<h3 ><a>Launch:</a></h3>

```---```

<h3 ><a>Class description</a></h3>
>root of the project:
- ```main``` - application code directory
-  ```test``` - test code directory

>```main/java/com/javarush/todoapp```:
- ```model``` - contains the structures of the main objects of the application
- ```dto``` - contains views for models
- ```mappers``` - interfaces for converting views and models
- ```exceptions``` - contains possible errors from the application
- ```repositories``` - data link layer
- ```service``` - contains the business logic of the application
- ```servlits``` - servlet subpackage

also, at the root of the package are classes:

- ```AppContextListener``` - loads application data into the context
- ```DbConfiguration``` - database connection setup
- ```LiquibaseConnect``` - database migration (creation of initial tables, partial filling with data)

>```main/resources``` contain: log4j2.xml

>```src/main/webapp/WEB-INF```:
- ```createTeg.html``` - page for creating new teg
- ```dashboard.html``` - general page with user's tasks
- ```editTask.html``` - page for editing one of the task
- ```task.html``` - page for creating new task
- ```index.html``` - starting page

<h3><a>Technologies in the project</a></h3>

- Maven
- UI: HTML, jQuery, Bootstrap, CSS
- db: Hibernate, PostgreSQL, liquibase
- lib's: apache-commons, lombok, log4g, p6spy, mapstruct
