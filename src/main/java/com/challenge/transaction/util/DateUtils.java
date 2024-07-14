package com.challenge.transaction.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

  public static final String AMERICA_LIMA = "America/Lima";
  public static final ZoneId LIMA_TIME_ZONE = ZoneId.of(AMERICA_LIMA);

  public static LocalDateTime getDateTimeLimaZone() {
    return LocalDateTime.ofInstant(Instant.now(), LIMA_TIME_ZONE);
  }
}
