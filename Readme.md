# Craft Demo - Player Management System

This project is a Spring Boot REST API for managing players, teams, and managers. It includes entity relationships, role-based filtering, and a fun AI nickname generator.

---

## Tech Stack

- Java 21
- Spring Boot 3.2
- Spring Data JPA
- H2 In-Memory Database
- Lombok
- IntelliJ IDEA

---

## 🔧 Setup Instructions

### Clone the Repo
```bash
git clone https://github.com/your-username/craft-demo.git
cd craft-demo
```

### Run the App (IntelliJ or Terminal)
```bash
./mvnw spring-boot:run
```

Or run `PlayerServiceApplication.java` from IntelliJ.

###  H2 Console
Access at: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:playerdb`
- User: `sa`
- Password: *(leave blank)*

---

##  Features

- Player → Team (ManyToOne)
- Team → Manager (OneToOne)
- REST API for Players & Teams
- Filtered data via `isAdmin` flag
- AI-style Nickname Generator
- Clean H2 schema with auto-seeding

---

## API Endpoints

###  Get All Players
```
GET /v1/players?isAdmin=true
```

###  Get Player by ID
```
GET /v1/players/{id}?isAdmin=false
```

###  Get Players by Team ID
```
GET /v1/players/by-team/{teamId}?isAdmin=true
```

### Get All Teams
```
GET /v1/teams
```

### Get AI Nickname
```
GET /v1/nickname?name=Virat&country=India
```

---

## Bonus: Nickname Generator
- Dynamically assigns nicknames based on country
- Falls back to LLM-style logic when country is unknown

---

## Deployment (Optional)
You can deploy this to [https://render.com](https://render.com):
1. Connect your GitHub repo
2. Set build command: `./mvnw clean package`
3. Set start command: `java -jar target/PlayerService-0.0.1-SNAPSHOT.jar`
4. Render gives you a public URL to access the APIs

---

## Author
**Sriram Nidamanuri**

---

## License
This project is open-source and free to use.

