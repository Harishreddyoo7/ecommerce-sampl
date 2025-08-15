# E-Commerce Sample (Spring Boot WAR for Tomcat + Jenkins + Maven)

Minimal e-commerce web app with login/registration, product catalog, and cart.
- **Tech**: Spring Boot 2.7.x (WAR), Spring MVC + Thymeleaf, Spring Data JPA, Spring Security, H2 (default) or MySQL (optional).
- **Deploy**: Build with Maven → produce `war` → copy to Tomcat's `webapps/` (Jenkinsfile included).

## Quick start (local dev)

Requirements: JDK 8 or 11 or 17, Maven 3.8+

```bash
mvn clean package
java -jar target/ecommerce-sample-war.war
# App runs with embedded Tomcat for dev at http://localhost:8080
# Default users created at startup: admin@example.com / admin123, user@example.com / user123
```

## Switch to external Tomcat (WAR)

- The build already produces a **WAR** (`ecommerce-sample-war.war`) that is deployable to external Tomcat 9.x.
- For Tomcat 9, Boot 2.7 uses `javax.*` so compatibility is OK.
- Copy the WAR to `$CATALINA_BASE/webapps/` (see Jenkinsfile).

## H2 vs MySQL

- Default is **H2 in file mode** at `./data/ecomdb`. H2 console: `/h2-console` (JDBC URL `jdbc:h2:file:./data/ecomdb`).
- To use MySQL, set env vars or edit `application-mysql.properties` and run with `--spring.profiles.active=mysql`.

## Jenkins

1. Create Jenkins credentials `deploy_ssh` (SSH username + private key) for your Tomcat box.
2. Add environment variables in Jenkins job:
   - `DEPLOY_HOST` (e.g., `10.0.0.5`)
   - `DEPLOY_USER` (e.g., `tomcat`)
   - `DEPLOY_PATH` (Tomcat `webapps` dir, e.g., `/opt/tomcat/webapps`)
3. Ensure server trusts Jenkins SSH key and user has write access to `webapps/`.
4. Pipeline will: checkout → `mvn -B clean package` → scp the WAR → (optional) touch `reload` for context reload.

## Default endpoints

- `/` – Home & product list
- `/auth/register`, `/auth/login`
- `/cart`, `/cart/add/{productId}`, `/cart/remove/{itemId}`
- `/h2-console` (dev only)

## Notes
This is intentionally minimal but cleanly layered so you can extend to orders/checkout.
