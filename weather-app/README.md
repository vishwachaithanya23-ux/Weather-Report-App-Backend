# Weather App Starter (Spring Boot + Angular)

This archive contains:
- backend/ : Spring Boot backend (WebFlux, Caffeine cache)
- frontend/: Angular 16+ simple frontend

## Quickstart

1. Backend:
   - Set environment variable OPENWEATHER_API_KEY to your OpenWeatherMap API key.
   - Build and run:
     mvn -f backend/pom.xml clean package
     java -jar backend/target/weather-backend-0.0.1-SNAPSHOT.jar

2. Frontend:
   - cd frontend
   - npm install
   - npm start

API endpoint examples:
- http://localhost:8080/api/weather/search?q=London
- http://localhost:8080/api/weather/current?lat=19.0760&lon=72.8777&units=metric

