import { getPlatformPageConfig } from '@/stores/platform'
import {
  deviceTypeOptions,
  enableOptions,
  guideOptions,
  languageOptions,
  loadConfigOptions,
  showOptions
} from './shared'

const staticOptionSources = {
  deviceTypeOptions,
  enableOptions,
  guideOptions,
  languageOptions,
  showOptions
}

async function resolveConfiguredFields(fields = []) {
  const needsConfigOptions = fields.some((field) => field.optionSource && !staticOptionSources[field.optionSource])
  const configOptions = needsConfigOptions ? await loadConfigOptions() : {}

  return fields.map((field) => {
    if (!field.optionSource) {
      return field
    }
    const { optionSource, ...rest } = field
    return {
      ...rest,
      options: staticOptionSources[optionSource] || configOptions[optionSource] || []
    }
  })
}

function resolveConfiguredFieldSource(fields) {
  if (!Array.isArray(fields)) {
    return fields
  }
  return () => resolveConfiguredFields(fields)
}

export function mergePlatformPageConfig(pageCode, fallbackConfig) {
  const pageConfig = getPlatformPageConfig(pageCode)
  if (!pageConfig) {
    return fallbackConfig
  }

  return {
    ...fallbackConfig,
    ...pageConfig,
    defaults: {
      ...(fallbackConfig.defaults || {}),
      ...(pageConfig.defaults || {})
    },
    searchFields: resolveConfiguredFieldSource(pageConfig.searchFields) || fallbackConfig.searchFields,
    formFields: resolveConfiguredFieldSource(pageConfig.formFields) || fallbackConfig.formFields
  }
}
