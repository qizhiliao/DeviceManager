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
      <br><br><br><br>
    </div>
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <!-- 标题 -->
      <div class="title-container">
        <h3 class="title">设备租赁管理系统-注册界面</h3>
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
          ref="password"
          v-model="loginForm.password"
          type="password"
          placeholder="请输入密码"
          name="password"
          tabindex="2"
          auto-complete="on"
        />
      </el-form-item>
      <!-- 确认密码 -->
      <el-form-item prop="repeat">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          class="yuan"
          ref="repeat"
          v-model="loginForm.repeat"
          type="password"
          placeholder="请确认密码"
          name="repeat"
          tabindex="3"
          auto-complete="on"
          @keyup.enter.native="handleRight"
        />
      </el-form-item>

      <!-- 登录按钮 -->
      <div style="height: 40px; margin-bottom: 30px">
        <el-button :loading="loading" type="primary" style="width: 48%; float: left" @click.native.prevent="handleRight">确认</el-button>
        <el-button :loading="loading" type="success" style="width: 48%; float: right" @click.native.prevent="handleBack">返回登录</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { register } from '@/api/user'

export default {
  name: 'Login',
  data() {
    const validateRepeat = (rule, value, callback) => {
      if (value !== this.loginForm.password) {
        callback(new Error('两次输入的密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        userName: '',
        password: '',
        repeat: ''
      },
      loginRules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        repeat: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { trigger: 'blur', validator: validateRepeat }
        ]
      },
      loading: false
    }
  },
  methods: {
    handleRight() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          register({ userName: this.loginForm.userName, password: this.loginForm.password}).then((res) => {
            if (res === 0) {
              this.$message.error('注册失败，可能账号重复了')
            } else {
              this.$message.success('注册成功')
            }
          })
        } else {
          console.log('不允许提交!')
          return false
        }
      })
    },
    handleBack() {
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #0f172a; // 科技风深色背景
$light_gray: #e0f7ff; // 亮青色文字
$cursor: #ffffff; // 霓虹青色光标
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
    padding: 40px;

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
$bg: #0f172a;
$dark_gray: #ffffff; // 霓虹青色
$light_gray: #ffffff;

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
