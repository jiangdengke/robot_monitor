package org.jdk.project.service.config;

import org.jdk.project.exception.BusinessException;

final class ConfigCommandSupport {

  private ConfigCommandSupport() {}

  static Long requiredId(Long value, String message) {
    if (value == null) {
      throw new BusinessException(message);
    }
    return value;
  }

  static int defaultInt(Integer value, int fallback) {
    return value == null ? fallback : value;
  }

  static String defaultString(String value, String fallback) {
    return value == null ? fallback : value;
  }

  static void ensureUpdated(int updated, String message) {
    if (updated == 0) {
      throw new BusinessException(message);
    }
  }
}
