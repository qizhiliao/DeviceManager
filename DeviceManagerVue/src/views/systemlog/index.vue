<template>
  <div class="system-log-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>系统日志管理</span>
      </div>
      <el-table
        v-loading="listLoading"
        :data="logList"
        border
        fit
        highlight-current-row
      >
        <el-table-column type="index" label="序号" width="70"></el-table-column>
        <el-table-column prop="id" label="日志ID" width="120"></el-table-column>
        <el-table-column prop="operation" label="操作类型" width="150"></el-table-column>
        <el-table-column prop="username" label="操作用户" width="120"></el-table-column>
        <el-table-column prop="ip" label="IP地址" width="140"></el-table-column>
        <el-table-column prop="time" label="操作时间" width="180"></el-table-column>
        <el-table-column prop="detail" label="操作详情"></el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="listQuery.limit"
          :current-page.sync="listQuery.page"
          @current-change="handleCurrentChange"
        ></el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'SystemLog',
  data() {
    return {
      logList: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10
      }
    }
  },
  created() {
    this.getLogList()
  },
  methods: {
    getLogList() {
      this.listLoading = true
      // 使用mock数据避免调用后端接口
      setTimeout(() => {
        this.logList = [
          {
            id: '1',
            operation: '登录',
            username: 'admin',
            ip: '192.168.1.1',
            time: '2023-11-01 09:30:00',
            detail: '用户admin登录系统'
          },
          {
            id: '2',
            operation: '设备添加',
            username: 'admin',
            ip: '192.168.1.1',
            time: '2023-11-01 10:15:30',
            detail: '添加新设备: 相机-001'
          }
        ]
        this.total = 2
        this.listLoading = false
      }, 1000)
    },
    handleCurrentChange(page) {
      this.listQuery.page = page
      this.getLogList()
    }
  }
}
</script>

<style scoped>
.pagination-container {
  padding: 15px 15px 0 15px;
  text-align: right;
}
</style>