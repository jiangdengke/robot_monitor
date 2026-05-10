<template>
  <el-card shadow="never">
    <template #header>
      <el-space direction="vertical" alignment="flex-start">
        <el-text tag="b" size="large">后台首页</el-text>
        <el-text type="info">当前首页展示本地单体后台状态和恢复进度。</el-text>
      </el-space>
    </template>
    <el-row :gutter="16">
      <el-col :xs="24" :md="12">
        <el-card shadow="never">
          <template #header>
            <el-text tag="b">后端状态</el-text>
          </template>
          <el-space direction="vertical" alignment="flex-start">
            <el-text>单体 Spring Boot 后端已本地启动。</el-text>
            <el-text>接口前缀：`/api`</el-text>
          </el-space>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card shadow="never">
          <template #header>
            <el-text tag="b">前端状态</el-text>
          </template>
          <el-text>页面已从静态包重建为可维护的 `.vue` 源码，并接入本地后台接口。</el-text>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>
