# Banking Platform - API Gateway Path Rewriting

| Component        | Port | Path exposed                      |
|------------------|------|-----------------------------------|
| account-service  | 8081 | /api/v1/internal/accounts/**      |
| api-gateway      | 8080 | /accounts/**  (public)            |

Gateway rewrites `/accounts/**` -> `/api/v1/internal/accounts/**` before forwarding.

## Run in Eclipse STS
1. File > Import > Maven > Existing Maven Projects > select the `banking-platform` folder > tick all 3 projects > Finish.
2. Wait for Maven to download dependencies (Project > Maven > Update Project, Alt+F5, if errors persist).
3. Open the **Boot Dashboard** (Window > Show View > Spring Boot Dashboard).
4. Start `account-service` first, then `api-gateway` (right-click > (Re)start).
   Or: right-click `AccountServiceApplication.java` > Run As > Spring Boot App; same for `ApiGatewayApplication.java`.

## Run from command line (alternative)
    mvn -pl account-service spring-boot:run
    mvn -pl api-gateway spring-boot:run
    mvn -pl api-gateway spring-boot:run -Dspring-boot.run.profiles=stripprefix   # StripPrefix variant

## Verify
    curl http://localhost:8081/api/v1/internal/accounts/ping     # direct backend
    curl http://localhost:8080/accounts/ping                      # via gateway
    curl http://localhost:8080/accounts
    curl http://localhost:8080/accounts/1001
    curl -X POST http://localhost:8080/accounts -H "Content-Type: application/json" -d "{\"holderName\":\"Meera\",\"type\":\"SAVINGS\",\"balance\":5000}"

`/accounts/ping` should return `"receivedPath":"/api/v1/internal/accounts/ping"`.
Console logs: gateway prints `GATEWAY PATH: /accounts/ping -> /api/v1/internal/accounts/ping`,
account-service prints `BACKEND RECEIVED -> GET /api/v1/internal/accounts/ping`.
