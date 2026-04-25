# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Build (skip tests)
./mvnw clean package -DskipTests

# Run (development)
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run single test class
./mvnw test -Dtest=ForecastApplicationTests
```

**Output JAR:** `target/forecast-2.0.jar`

**Scripts (Raspberry Pi deployment):**
- `run.sh` — start the JAR (used by systemd)
- `start_forecast.sh` — open Chromium in kiosk mode
- `stop.sh` — kill whatever is on port 8080

## Architecture

Spring Boot 3.5.8, Java 21, Thymeleaf templates. Designed as a weather kiosk for Raspberry Pi.

**Request flow:**
1. `GET /forecast` → `BaseController.weather()` in [src/main/java/com/naturecode/forecast/app/BaseController.java](src/main/java/com/naturecode/forecast/app/BaseController.java)
2. Checks `GpsCache` for a live GPS fix; falls back to `latitude`/`longitude` from `application.properties`
3. Calls `Credential.getForecastFromWeatherApi()` → `api.weatherapi.com/v1/forecast.json` (5-day, no AQI/alerts)
4. Calls `Utils.getLunarDate()` (IBM ICU4J `ChineseCalendar`) for the lunar date display
5. Maps weather codes → Skycons icon names via `Utils.getIcon()`
6. Renders `src/main/resources/templates/index.html` (Thymeleaf) — page auto-refreshes every 600 s

**GPS thread:**
- `GpsReaderService` starts a daemon thread on `@PostConstruct`
- Reads NMEA RMC sentences from `/dev/ttyACM0` at 9600 baud (jSerialComm)
- Writes fixes into `GpsCache` (`AtomicReference<GpsFix>`)
- `gps_continous_read=false` in `application.properties` → read once then stop; `true` → keep polling

**Key config (`application.properties`):**
```
latitude / longitude      # fallback coordinates (Washington DC area)
gps_continous_read        # controls GPS polling loop
```

**Weather API key** is hardcoded in `Credential.java` — move to `application.properties` or an env var before exposing the repo publicly.

**Frontend:** black-background flexbox layout; left pane shows date/time/location/lunar date, right pane shows current temp + 4-day forecast cards. Skycons (`skycons.js`) renders animated SVG weather icons on `<canvas>` elements. JavaScript `startTime()` ticks the clock every second.
