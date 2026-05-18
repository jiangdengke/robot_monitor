package org.jdk.project.service.digitaltwin;

final class DigitalTwinSupport {

  private DigitalTwinSupport() {}

  static String trimToNull(String value) {
    if (value == null || value.trim().isEmpty()) {
      return null;
    }
    return value.trim();
  }

  static String firstNonBlank(String... values) {
    for (String value : values) {
      String normalized = trimToNull(value);
      if (normalized != null) {
        return normalized;
      }
    }
    return null;
  }

  static Long parseLong(String value) {
    String normalized = trimToNull(value);
    if (normalized == null) {
      return null;
    }
    try {
      return Long.valueOf(normalized);
    } catch (NumberFormatException ignored) {
      return null;
    }
  }
}
