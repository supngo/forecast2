package com.naturecode.forecast.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class GpsCache {
  private final AtomicReference<GpsFix> latestFix = new AtomicReference<>();

  public void update(GpsFix fix) {
    latestFix.set(fix);
  }

  public GpsFix getLatest() {
    return latestFix.get();
  }

  public record GpsFix(double latitude, double longitude, Instant timestamp) {}
}
