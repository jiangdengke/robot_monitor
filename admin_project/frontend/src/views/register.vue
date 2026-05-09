<template>
  <div class="auth-shell">
    <el-card class="auth-card">
      <h1>注册账号</h1>
      <p>当前后端注册接口是否开放由系统参数控制，页面已预留为真实源码表单。</p>
      <el-form label-position="top" class="auth-form" @submit.prevent>
        <el-form-item label="用户名">
          <el-input placeholder="用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" show-password placeholder="密码" />
        </el-form-item>
        <el-form-item label="验证码">
          <el-input placeholder="验证码（按需启用）" />
        </el-form-item>
        <el-button type="primary" native-type="submit">提交注册</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.auth-shell { min-height: 100vh; display: grid; place-items: center; padding: 24px; }
.auth-card { width: min(520px,100%); padding: 32px; }
.auth-card h1 { margin: 0; font-size: 28px; }
.auth-card p { margin: 10px 0 0; color: var(--text-soft); }
.auth-form { margin-top: 20px; }
</style>
