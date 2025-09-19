# 📝 Notes API

A RESTful service for managing notes and authors, built with Spring Boot. This project includes full CRUD operations, DTO mapping via MapStruct, input validation, and interactive API documentation using Swagger.

---

## 🚀 Technologies Used

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **H2 (in-memory database)**
- **MapStruct** – automatic DTO ↔ entity mapping
- **Lombok** – boilerplate reduction (getters, constructors, etc.)
- **Swagger UI (via WebJar)** – Interactive API documentation powered by Swagger UI 5.28.1, manually integrated without Springdoc.

---

## 📦 Project Structure

src/ 
├── controller/ # REST controllers 
├── service/ # Business logic 
├── repository/ # JPA interfaces 
├── model/ # JPA entities 
├── dto/ # Data Transfer Objects 
├── mapper/ # MapStruct mappers


---

## 📚 API Endpoints

### 🔹 Author

- `POST /authors` – Create a new author
- `GET /authors` – Retrieve all authors
- `GET /authors/{id}` – Retrieve author by ID

### 🔹 Note

- `POST /notes` – Create a new note
- `GET /notes` – Retrieve all notes
- `GET /notes/{id}` – Retrieve note by ID
- `DELETE /notes/{id}` – Delete note by ID

---

## 🧪 Validation

- Uses `@NotBlank`, `@NotNull` for DTO field validation
- Controllers handle validation via `@Valid`

---

## 🧭 Swagger UI

After starting the application, access the interactive API documentation at:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

This Swagger UI is manually integrated using the latest WebJar (`swagger-ui:5.28.1`) for full control over versioning and security.

