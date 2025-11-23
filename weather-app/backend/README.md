# Weather Backend (Spring Boot)

## Setup
1. Set environment variable `OPENWEATHER_API_KEY` with your OpenWeatherMap API key.
2. Build:
   mvn clean package
3. Run:
   java -jar target/weather-backend-0.0.1-SNAPSHOT.jar

## Endpoints
- GET /api/weather/current?lat={lat}&lon={lon}&units=metric
- GET /api/weather/search?q={city}
