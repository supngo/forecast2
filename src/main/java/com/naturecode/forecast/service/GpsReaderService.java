package com.naturecode.forecast.service;

import com.fazecast.jSerialComm.SerialPort;
import com.naturecode.forecast.util.GpsCache;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;

@Service
public class GpsReaderService {
  private final GpsCache cache;

  @Value("${gps_continous_read: false}")
  private boolean isContinous;

  public GpsReaderService(GpsCache cache) {
    this.cache = cache;
  }

  @PostConstruct
  public void start() {
    Thread gpsThread = new Thread(this::readLoop, "gps-reader");
    gpsThread.setDaemon(true);
    gpsThread.start();
  }

  private void readLoop() {
    SerialPort port = SerialPort.getCommPort("/dev/ttyACM0");
    port.setComPortParameters(9600, 8,
        SerialPort.ONE_STOP_BIT,
        SerialPort.NO_PARITY);

    port.setComPortTimeouts(
        SerialPort.TIMEOUT_READ_SEMI_BLOCKING,
        1000,
        0);

    if (!port.openPort()) {
      throw new IllegalStateException("Cannot open GPS USB");
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(port.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("$GPRMC") || line.startsWith("$GNRMC")) {
          GpsCache.GpsFix fix = parseRMC(line);
          if (fix != null) {
            cache.update(fix); // ✅ publish latest fix
            if (!isContinous) {
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); // consider restart strategy
    } finally {
      port.closePort();
    }
  }

  private GpsCache.GpsFix parseRMC(String sentence) {
    String[] p = sentence.split(",");

    if (p.length < 7 || !"A".equals(p[2])) {
      return null;
    }

    double lat = convert(p[3], p[4]);
    double lon = convert(p[5], p[6]);

    if (lat == 0 && lon == 0)
      return null;

    return new GpsCache.GpsFix(lat, lon, Instant.now());
  }

  private double convert(String raw, String dir) {
    int dot = raw.indexOf('.');
    int degLen = (dot > 4) ? 3 : 2;

    double deg = Double.parseDouble(raw.substring(0, degLen));
    double min = Double.parseDouble(raw.substring(degLen));

    double val = deg + min / 60.0;
    return ("S".equals(dir) || "W".equals(dir)) ? -val : val;
  }
}
