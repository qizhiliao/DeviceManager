<!--
 * @Description:
 * @Author: Rabbiter
 * @Date: 2023-02-21 18:13:21
-->
<template>
  <div class="dashboard-container">
    <div title="欢迎页面" style="padding: 50px; overflow: hidden; color: #409eff;font: lighter 16px 'Lucida Sans Unicode';"
         data-options="iconCls:'icon-heart',plain:true">
        <b style="font-size: 36px; line-height: 30px; height: 30px;">欢迎使用摄影设备租赁管理系统</b>
        <hr>
        <b>项目开发环境介绍</b>
        <p>操作系统: Windows</p>
        <p>开发工具: Intellij IDEA 2023.2.1</p>
        <p>服务器: Tomcat 8.5</p>
        <p>数据库: MySQL 8.0.33</p>
        <p>前端技术: HTML+CSS+JavaScript+Vue+Axios+ElementUI</p>
        <p>后端技术: Java+SpringBoot+MyBatis+MySQL</p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'id',
      'name',
      'roles'
    ])
  }
}
</script>
