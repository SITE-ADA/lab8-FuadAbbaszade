# University System

This project is a small university management system built as a Spring Boot multi-module project. It contains separate services for student management and course management, including enrollment, prerequisite validation, and course lookup by student name.

## Technologies

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Validation
- Spring Cloud OpenFeign
- PostgreSQL
- Docker Compose
- Springdoc OpenAPI / Swagger UI
- Gradle

## Project Structure

- `student-service`: student CRUD and student name search
- `course-service`: course CRUD, enrollments, prerequisite rules, and course lookup by student name

## Business Rules

- `Course` may have one optional `prerequisiteCourseId`.
- `Enrollment` stores `studentId`, `courseId`, `enrollmentDate`, and `status`.
- `EnrollmentStatus` values are `ACTIVE` and `COMPLETED`.
- A student can enroll in a course with a prerequisite only if that student already has an enrollment for the prerequisite course with status `ACTIVE` or `COMPLETED`.

## How to Run with Docker

1. Start all services and databases:

```bash
docker compose up --build
```

2. Service URLs:

- `student-service`: `http://localhost:9090`
- `course-service`: `http://localhost:8081`

3. Swagger URLs:

- `http://localhost:9090/swagger-ui/index.html`
- `http://localhost:8081/swagger-ui/index.html`

## How to Run Locally

1. Start PostgreSQL instances.
2. Create two databases:

- `studentDB`
- `courseDB`

3. Default credentials expected by the application:

- username: `postgres`
- password for both databases: `password`

4. Start the student service:

```bash
./gradlew :student-service:bootRun
```

5. Start the course service in a separate terminal:

```bash
./gradlew :course-service:bootRun
```

## Main Endpoints

### Student Service

- `POST /api/v1/students`
- `GET /api/v1/students`
- `GET /api/v1/students/{id}`
- `PUT /api/v1/students/{id}`
- `DELETE /api/v1/students/{id}`
- `GET /api/v1/students/search?name=ali`

### Course Service

- `POST /api/v1/courses`
- `GET /api/v1/courses`
- `GET /api/v1/courses/{id}`
- `PUT /api/v1/courses/{id}`
- `DELETE /api/v1/courses/{id}`
- `POST /api/v1/courses/{courseId}/students/{studentId}`
- `PATCH /api/v1/courses/{courseId}/students/{studentId}/status`
- `GET /api/v1/courses/{courseId}/students`
- `GET /api/v1/courses/search/by-student-name?name=ali`

## Example Requests

Create a student:

```http
POST /api/v1/students
Content-Type: application/json

{
  "firstName": "Nicat",
  "lastName": "Aliyev",
  "email": "nicat.aliyev@example.com",
  "age": 20
}
```

Create a course without a prerequisite:

```http
POST /api/v1/courses
Content-Type: application/json

{
  "title": "Programming Fundamentals",
  "code": "CS101",
  "credits": 5,
  "prerequisiteCourseId": null
}
```

Create a course with a prerequisite:

```http
POST /api/v1/courses
Content-Type: application/json

{
  "title": "Data Structures",
  "code": "CS201",
  "credits": 4,
  "prerequisiteCourseId": 1
}
```

Enroll a student:

```http
POST /api/v1/courses/2/students/1
```

Mark an enrollment as completed:

```http
PATCH /api/v1/courses/2/students/1/status
Content-Type: application/json

{
  "status": "COMPLETED"
}
```

Search courses by student name:

```http
GET /api/v1/courses/search/by-student-name?name=ali
```

## Notes

- `course-service` communicates with `student-service` using Feign.
- Swagger endpoint descriptions and DTO field descriptions are written in Azerbaijani.
- The included tests are basic module sanity tests; API behavior should also be verified manually through Swagger or an HTTP client.
