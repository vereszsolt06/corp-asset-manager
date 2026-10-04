# Corporate Asset Manager

Céges eszköznyilvántartó: eszközök (laptop, telefon, autó, monitor) és dolgozók kezelése,
eszközök kiadása dolgozóknak (1:N kapcsolat).

**Technológiák:** Spring Boot 3.5 (Java 25), Spring Data JPA, H2, Spring Security (HTTP Basic), React 19 + Vite

## Futtatás
    mvn spring-boot:run
Ezután: http://localhost:8081
(A Maven a frontend buildhez szükséges Node.js-t automatikusan letölti.)

## Belépés
| Felhasználó | Jelszó |
|---|---|
| admin | admin |
| user | user |

## Fejlesztői mód
- Backend: IntelliJ-ből az `Application` osztály (8081)
- Frontend: `cd frontend`, `npm install`, `npm run dev` → http://localhost:5173

## H2 konzol
http://localhost:8081/db — JDBC URL: `jdbc:h2:mem:corp_assets`, felhasználó: `sa`, jelszó üresen