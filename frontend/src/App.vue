<template>
  <a-config-provider :theme="theme" :locale="zhCN">
    <router-view />
  </a-config-provider>
</template>

<script setup>
import { theme as antdTheme } from 'ant-design-vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN'

const theme = {
  token: {
    colorPrimary: '#2f54eb',
    colorInfo: '#2f54eb',
    colorSuccess: '#52c41a',
    colorWarning: '#faad14',
    colorError: '#ff4d4f',
    colorTextBase: '#1f2937',
    colorBgContainer: '#ffffff',
    colorBgLayout: '#f3f5fa',
    colorBorder: '#e5e9f2',
    colorBorderSecondary: '#eef1f8',
    borderRadius: 10,
    borderRadiusSM: 6,
    borderRadiusLG: 14,
    fontSize: 14,
    fontFamily: '"Inter", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Arial, sans-serif',
    controlHeight: 36,
    boxShadow: '0 4px 14px rgba(15, 23, 42, 0.06)',
    boxShadowSecondary: '0 12px 32px rgba(15, 23, 42, 0.08)'
  },
  algorithm: antdTheme.defaultAlgorithm,
  components: {
    Layout: {
      headerBg: '#ffffff',
      siderBg: '#0f1d3a',
      bodyBg: '#f3f5fa',
      headerHeight: 60,
      headerPadding: '0 24px'
    },
    Menu: {
      darkItemBg: 'transparent',
      darkSubMenuItemBg: 'transparent',
      darkItemSelectedBg: 'rgba(47, 84, 235, 0.22)',
      darkItemHoverBg: 'rgba(255, 255, 255, 0.06)',
      darkItemColor: 'rgba(232, 238, 255, 0.78)',
      darkItemSelectedColor: '#ffffff'
    },
    Button: {
      controlHeight: 36,
      paddingInline: 18
    },
    Card: {
      headerBg: 'transparent',
      headerFontSize: 15
    },
    Table: {
      headerBg: '#f7f9fc',
      headerColor: '#0f172a',
      rowHoverBg: 'rgba(47, 84, 235, 0.04)',
      headerSplitColor: 'transparent'
    },
    Tabs: {
      itemActiveColor: '#2f54eb',
      itemSelectedColor: '#2f54eb',
      itemHoverColor: '#5573f3',
      inkBarColor: '#2f54eb'
    }
  }
}
</script>
