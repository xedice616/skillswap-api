# SkillSwap API

SkillSwap is a RESTful backend application built with Java and Spring Boot that allows users to share their skills, send learning requests, manage learning interactions, and review other users after a completed learning experience.

The project focuses on relational data modeling, validation, business rules, exception handling, and clean layered architecture.

---

## Features

- Create and view users
- Publish and browse skills
- Filter skills by category
- View skills by owner
- Send learning requests for a specific skill
- View sent and received learning requests
- Accept or reject incoming requests
- Mark accepted requests as completed
- Write reviews after a completed learning process
- Prevent duplicate learning requests
- Prevent users from requesting their own skills
- Prevent users from reviewing themselves
- Prevent duplicate reviews
- Validate request data
- Handle errors with appropriate HTTP status codes
- Persist data using PostgreSQL

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Jakarta Bean Validation
- Maven
- Postman

---

## Architecture

The application follows a layered architecture:

```text
Client / Postman
      |
      v
Controller
      |
      v
Service
      |
      v
Repository
      |
      v
PostgreSQL
