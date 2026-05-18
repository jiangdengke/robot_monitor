package org.jdk.project.service.statistics;

final class StatisticsQuerySupport {

  private StatisticsQuerySupport() {}

  static String trimToNull(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return value.trim();
  }

  static String normalizeAccessType(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "1" -> "ID_CARD";
      case "2" -> "QRCODE";
      case "3" -> "FACE";
      default -> value;
    };
  }

  static String normalizePassengerStatus(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "1" -> "IN";
      case "0" -> "OUT";
      default -> value;
    };
  }
}
