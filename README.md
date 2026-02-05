# 💰 Smart Expense Tracker – Backend REST API

## 📌 Project Overview
**Smart Expense Tracker** is a **production-ready backend REST API** built using **Spring Boot** that enables users to securely manage personal expenses in a multi-user environment.
The project focuses on **real-world backend engineering concerns** such as authentication, secure user-scoped data access, pagination, exception handling, CI/CD, and cloud deployment—similar to enterprise financial systems.

---

## 🚀 Key Features
- User registration & login
- Secure authentication using JWT
- Password hashing with BCrypt
- User-scoped expense management (no userId exposure)
- Add & retrieve expenses for logged-in users
- Pagination & sorting for large datasets
- DTO-based API design (no entity leakage)
- Global exception handling
- CI/CD pipeline with GitHub Actions
- Deployed on Render (Dockerized)

---

## 🛠 Tech Stack
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JJWT)
- Docker
- GitHub Actions (CI)
- Render (Cloud Deployment)
- Maven
- Postman (API testing)

---

## 🧩 Architecture Overview
Controller
   ↓
Service
   ↓
Repository
   ↓
PostgreSQL

---

## 🔐 Detailed Authentication Flow

1. **Registration:**
   - User sends POST /api/users/register with credentials
   - Password hashed using BCryptPasswordEncoder (strength 10)
   - User entity saved to PostgreSQL

2. **Login:**
   - User sends POST /api/users/login with email & password
   - Spring Security validates credentials via UserDetailsService
   - JWT generated and returned

3. **Authorized Requests:**
   - Client includes JWT in Authorization header as "Bearer <token>"
   - Custom OncePerRequestFilter intercepts every request
   - Filter extracts and validates JWT
   - If valid, user details loaded into SecurityContext
   - Request proceeds to controller with authenticated user context

---

### Example: Login User
**Request:**
POST /api/users/login
Content-Type: application/json

{
  "email": "utkarsh@example.com",
  "password": "SecurePass123"
}

**Response:**
HTTP 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "utkarsh@example.com"
}

### Example: Add Expense (Authenticated)
**Request:**
POST /api/expenses/my
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "title": "Grocery Shopping",
  "amount": 1250.50,
  "category": "Food",
  "expenseDate": "2026-02-04"
}

**Response:**
HTTP 201 Created
{
  "id": 1,
  "title": "Grocery Shopping",
  "amount": 1250.50,
  "category": "Food",
  "expenseDate": "2026-02-04",
  "createdAt": "2026-02-04T10:30:00"
}

---

## 🔧 Technical Implementation Details

### JWT Configuration
- **Library Used:** `io.jsonwebtoken:jjwt-api`, `jjwt-impl`, `jjwt-jackson`
- **Signing Algorithm:** HS256 (HMAC-SHA256)
- **Token Structure:**
```
  Header: { "alg": "HS256", "typ": "JWT" }
  Payload: { "sub": "user@example.com", "userId": 1, "iat": 1707049800, "exp": 1707136200 }
  Signature: HMAC-SHA256(base64(header) + "." + base64(payload), secret)
```
- **Token Expiration:** 24 hours (configurable)
- **Secret Key:** Stored in application.properties (externalized for production)

### Custom Security Filter
- **Class:** `JwtAuthenticationFilter extends OncePerRequestFilter`
- **Purpose:** Intercepts requests to validate JWT before reaching controllers
- **Filter Chain Position:** Before `UsernamePasswordAuthenticationFilter`
- **Execution Flow:**
  1. Extract JWT from Authorization header
  2. Validate token signature and expiration
  3. Extract username from token claims
  4. Load UserDetails from database
  5. Create Authentication object and set in SecurityContext
  6. Pass request to next filter

### UserDetailsService Implementation
- **Class:** `CustomUserDetailsService implements UserDetailsService`
- **Purpose:** Load user from database for authentication
- **Method:** `loadUserByUsername(String email)` queries User entity
- **Returns:** `UserDetails` object with username, password, and authorities

---

## 🔐 Security Highlights
- Passwords are **never stored in plain text**
- BCrypt is used for password hashing with strength 10
- **JWT-based stateless authentication**
  - Token signed with HMAC-SHA256
  - Claims include userId, email, issued-at, and expiration
  - Secret key externalized for security
- Custom **OncePerRequestFilter** validates JWT on every request
- Expense APIs are scoped to the **logged-in user** via SecurityContext
- JWT extracted from **Authorization: Bearer <token>** header
- Prevents unauthorized access and horizontal privilege escalation
- No direct userId exposure in secured endpoints
- CSRF disabled for stateless REST API
- CORS configured for specific origins (production-ready)

---

## 🔄 JWT Authentication Flow Diagram
```
┌─────────┐                                    ┌─────────────┐
│ Client  │                                    │   Server    │
└────┬────┘                                    └──────┬──────┘
     │                                                │
     │  1. POST /login {email, password}             │
     │──────────────────────────────────────────────>│
     │                                                │
     │                        2. Validate credentials │
     │                        3. Generate JWT         │
     │                                                │
     │  4. Return JWT token                          │
     │<──────────────────────────────────────────────│
     │                                                │
     │  5. GET /api/expenses/my                      │
     │     Authorization: Bearer <JWT>               │
     │──────────────────────────────────────────────>│
     │                                                │
     │               6. OncePerRequestFilter extracts │
     │                  and validates JWT             │
     │               7. Load user from SecurityContext│
     │               8. Fetch user's expenses         │
     │                                                │
     │  9. Return expenses                           │
     │<──────────────────────────────────────────────│
     │                                                │
```

---

## 📡 API Endpoints (Sample)

### User APIs
| Method | Endpoint | Description |
|------|--------|------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Authenticate user and return JWT |

### Expense APIs
| Method | Endpoint | Description |
|------|--------|------------|
| POST | `/api/expenses/my` | Add expense for logged-in user |
| GET | `/api/expenses/my` | Get all expenses |
| GET | `/api/expenses/my/paged?page=0&size=5` | Get paginated expenses |

---

## ▶️ How to Run Locally

### Prerequisites
- Java 17
- Maven
- PostgreSQL must be running and database created before starting the app

### Steps
git clone https://github.com/hsraktu-18/smart-expense-tracker.git
cd smart-expense-tracker
mvn clean install
mvn spring-boot:run

---

## 🧪 Testing Strategy

**Unit Tests:**
- Service layer business logic
- Password encoding validation
- DTO mapping

**Integration Tests:**
- Controller endpoints with MockMvc
- Database operations with @DataJpaTest
- Security configuration with @WithMockUser

**Tools Used:**
- JUnit 5
- Mockito
- Spring Boot Test
- H2 for test database

---

## 🔧 Configuration Setup

1. Copy the example properties file:
```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
```

2. Update the following values in `application.properties`:
   - `spring.datasource.username`: Your PostgreSQL username
   - `spring.datasource.password`: Your PostgreSQL password
   - `jwt.secret`: Generate a secure secret key (256+ bits)
   
3. Generate JWT secret (run in terminal):
```bash
   openssl rand -base64 32
```

---

## 📦 Database Schema

### Users Table
| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGSERIAL | PRIMARY KEY |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(255) | UNIQUE, NOT NULL |
| password | VARCHAR(255) | NOT NULL (BCrypt hash) |
| created_at | TIMESTAMP | DEFAULT NOW() |

### Expenses Table
| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGSERIAL | PRIMARY KEY |
| title | VARCHAR(200) | NOT NULL |
| amount | DECIMAL(10,2) | NOT NULL |
| category | VARCHAR(50) | |
| expense_date | DATE | NOT NULL |
| user_id | BIGINT | FOREIGN KEY → users(id) |

**Indexes:** user_id, expense_date (for query optimization)

---

## 🐛 Common Issues & Solutions

**Issue:** "Invalid JWT signature" error
- **Cause:** Secret key mismatch or token tampered
- **Solution:** Verify secret key consistency across services; never expose secret

**Issue:** 401 Unauthorized even with valid token
- **Cause:** Token expired or SecurityContext not set
- **Solution:** Check token expiration time; ensure filter sets Authentication in SecurityContext

**Issue:** 403 Forbidden on /api/expenses/my
- **Cause:** CSRF protection blocking POST requests
- **Solution:** Disabled CSRF for stateless API (documented security implications)

**Issue:** Cannot find user expenses after login
- **Cause:** Not extracting user from SecurityContext correctly
- **Solution:** Used `(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()`

**Issue:** "Token must not be null or empty" error
- **Cause:** Missing Authorization header or incorrect format
- **Solution:** Ensure header format is "Bearer <token>" with space after Bearer

---

## 📚 Key Takeaways & Interview Talking Points

**Security:**
- Implemented BCrypt with salt rounds for password hashing
- Understood difference between authentication (who you are) vs authorization (what you can do)
- Prevented horizontal privilege escalation by scoping data to authenticated user
- Learned why storing passwords in plain text is a critical vulnerability

**Spring Ecosystem:**
- Configured Spring Security filter chain
- Used @PreAuthorize vs @Secured annotations [if applicable]
- Implemented custom UserDetailsService for database authentication
- Worked with SecurityContextHolder to retrieve authenticated user

**Database & JPA:**
- Designed one-to-many relationship (User → Expenses)
- Implemented pagination using Pageable interface

**API Design:**
- Followed DTO pattern to avoid exposing internal entity structure
- Implemented proper HTTP status codes (200, 201, 401, 403, 404)
- Used @ControllerAdvice for centralized exception handling


---

## 🎯 Key Design Decisions

**Why JWT over Session-based authentication?**
- Stateless: No server-side session storage needed
- Scalable: Easy to scale horizontally across multiple servers
- Mobile-friendly: Works seamlessly with mobile apps
- Cross-domain: Can be used across different domains
- Self-contained: Token contains all necessary user information

**Why OncePerRequestFilter?**
- Guarantees filter executes exactly once per request
- Prevents duplicate authentication attempts
- Handles async requests properly
- Part of Spring's standard filter chain

**Why HMAC-SHA256 (HS256) for signing?**
- Simpler than asymmetric algorithms (RS256)
- Sufficient for single-service architecture
- Faster computation
- Shared secret between client and server

---

## 🔮 Future Enhancements
- Role-based access control (ADMIN / USER)
- Refresh token support
- Token expiration & rotation
- OAuth2 / Social login
- Dockerization for deployment

---

## 👤 Author

**Utkarsh**  
Backend Developer | Spring Boot | Java
