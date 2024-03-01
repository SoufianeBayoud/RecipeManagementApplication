
# Recipe Management Application

This is a Recipe Management Application made for Cerba Research. This application allows you to manage recipes and their ingredients through a convenient JSON API. Below you'll find instructions for database configuration, running tests, and starting the application.

## Functional Requirements
### Database Model
The application's database consists of two main entities: Recipe and Ingredient.

### Recipe Entity Attributes:

![RecipeSchema](https://github.com/SoufianeBayoud/RecipeManagementApplication/assets/101556223/551a744a-a251-48d8-9e48-18b3c4952bff)

**Relationships:
One-to-Many relationship with Ingredient entity.**


### Ingredient Entity Attributes:

![IngredientSchema](https://github.com/SoufianeBayoud/RecipeManagementApplication/assets/101556223/77cc4c1f-958a-4754-a86c-4cf53251f473)

**Relationships: 
Many-to-One relationship with Recipe entity.**


## Technical Requirements

### Technology Stack
+ Programming Language: Java 
+ Build Automation: Maven
+ Application Framework: Spring Boot
+ Database: PostgreSQL
+ Libraries: 
```spring-boot-starter-data-jpa
spring-boot-starter-web
springdoc-openapi-starter-webmvc-ui
postgresql
junit
jackson-databind
mockito-core
lombok
spring-boot-starter-test
jackson-annotations
```
### Application Architecture
The application has a layered architecture, consisting of: 
+ Model layer: Entities linked to the database
+ Controller layer: Responsible for the CRUD-operations and the HTTP requests.
+ Service layer: Responsible for the business logic
+ Repository layer: Responsible for the data access



## API Reference
![Swagger CRUD operations](https://github.com/SoufianeBayoud/RecipeManagementApplication/assets/101556223/d7bd8040-728a-496a-ad0d-41628f37ec03)


#### Get all items

```http
GET /recipes
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get recipe by id
```http
GET /recipes/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of the item to fetch |


#### Get all recipes sorted by field

```http
GET /recipes/sorted/${field}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `field`      | `String` | **Required**. Field of item to sort |

#### Add a recipe
```http
POST /recipes
```
Adds a recipe to the **database**

#### Update a recipe 
```http
PUT /recipes/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**.  Id to fetch the recipe that you want to update |

#### Delete all recipes 
```http
DELETE //recipes/deleteAll
```


#### Delete a recipe by id
```http
PUT /recipes/${id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**.  Id to fetch the recipe that you want to delete |

#### Add ingredient to a recipe

```http
PUT /recipes/ingredients/add/${id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**.  Id to fetch the recipe that you want to add the ingredient |

#### Remove an ingredient from a recipe
```http
DELETE /recipes/ingredients/${id}/${ingredientId}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**.  Id of the recipe to fetch the recipe that you want
| `ingredientId`      | `Long` | **Required**.  Id to fetch the ingredient that you want to remove |












## Installation

To install and set up the `Recipe Management Application`, follow these steps:

 Clone the repository to your local machine:

```bash
git clone https://link-to-project
```

    
## Database Configuration Details

To configure the database for the application, modify the `application.properties` file located in the `src/main/resources` directory. Add or update the following properties with your database connection details:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/CerbaResearch
spring.datasource.username=username
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Make sure to replace `jdbc:postgresql://localhost:5432/CerbaResearch` with your actual database URL, `username` with your database username, and `password` with your database password.



## Run Locally

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```

Install dependencies

```bash
  mvn install
```

Start the server

```bash
  mvn spring-boot:run
```


## Running Tests

To run tests, run the following command

```bash
  mvn test
```


## Screenshots



