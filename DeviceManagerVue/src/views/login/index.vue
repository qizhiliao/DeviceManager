<template>
  <div class="login-container">
    <!-- 视频背景 -->
    <div class="video-background">
      <video autoplay loop muted playsinline class="bg-video">
        <source src="/videos/tech_background.mp4" type="video/mp4">
        您的浏览器不支持HTML5视频。
      </video>
      <div class="video-overlay"></div>
    </div>
    <div>
      <br>
      <br>
      <br>
      <br>
    </div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <!-- 标题 -->
      <div class="title-container">
        <h3 class="title">摄影设备租赁管理系统-登录界面</h3>
      </div>
      <!-- 用户名 -->
      <el-form-item prop="userName">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          class="yuan"
          ref="userName"
          v-model="loginForm.userName"
          placeholder="请输入用户名"
          name="userName"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <!-- 密码 -->
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          class="yuan"
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="请输入密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <!-- 权限 -->
      <el-form-item prop="authority">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-select v-model="loginForm.isAdmin" placeholder="请选择" style="width: 415px">
          <el-option :key="1" label="管理员" :value="1"></el-option>
          <el-option :key="0" label="用户" :value="0"></el-option>
        </el-select>
      </el-form-item>

      <!-- 登录按钮 -->
      <div style="height: 40px; margin-bottom: 30px;">
        <el-button :loading="loading" type="primary" style="width: 48%; float: left;" @click.native.prevent="handleLogin">登录</el-button>
        <el-button :loading="loading" type="success" style="width: 48%; float: right;" @click.native.prevent="handleRegister">注册</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      callback()
    }
    const validatePassword = (rule, value, callback) => {
      callback()
    }
    return {
      loginForm: {
        userName: 'admin',
        password: 'admin',
        isAdmin: 1
      },
      loginRules: {
        userName: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: '/' })  // 无脑进首页
            this.loading = false
          }).catch((message) => {
            this.$message.error(message)
            this.loading = false
          })
        } else {
          console.log('不允许提交!')
          return false
        }
      })
    },
    handleRegister() {
      console.log("注册按钮")
      this.$router.push({ path: '/register' })  // 进注册页面
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#0f172a; // 科技风深色背景
$light_gray:#e0f7ff; 
$cursor: #ffffff; 
$overlay_color: rgba(15, 23, 42, 0.5); // 视频叠加层颜色
$panel_bg: rgba(10, 24, 56, 0.8); // 登录面板半透明背景

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  position: relative;
  overflow: hidden;

  .video-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 0;

    .bg-video {
      position: absolute;
      top: 50%;
      left: 50%;
      min-width: 100%;
      min-height: 100%;
      width: auto;
      height: auto;
      transform: translate(-50%, -50%);
      object-fit: cover;
    }

    .video-overlay {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: $overlay_color;
    }
  }

  .login-form {
    position: relative;
    z-index: 1;
    background: $panel_bg; // 替换为纯色半透明背景
    border-radius: 10px; // 添加圆角效果
    //box-shadow: 0 0 30px rgba(255, 255, 255, 0.3); // 增强面板阴影
    padding: 40px;
    // 添加顶部空隙

    .el-input.yuan {
      display: inline-block;
      height: 47px;
      width: 415px;

      input {
        background: rgba(15, 23, 42, 0.1);
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-input {
      display: inline-block;
      height: 47px;
      width: 100%;

      input {
        background: rgba(19, 28, 50, 0.1);
        border: 0px;
        -webkit-appearance:none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.3);
      background: rgba(15, 23, 42, 0.5);
      box-shadow: 0 0 10px rgba(0, 240, 255, 0.2);
      transition: all 0.3s ease;
  
      &:focus-within {
        border-color: #ffffff;
        box-shadow: 0 0 15px rgba(255, 255, 255, 0.5);
      }
    }
  }
}
</style>

<style lang="scss" scoped>
$bg:#0f172a;
$dark_gray:#ffffff; // 霓虹青色
$light_gray:#ffffff;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 60px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
     color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

}
</style>
