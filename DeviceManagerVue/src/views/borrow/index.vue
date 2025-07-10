<template>
  <div class="app-container">
    <!-- 顶部功能 -->
    <div class="filter-container" style="margin-bottom: 15px">
      <!-- 用户名输入 -->
      <el-input v-permission="['admin']" v-model="queryParam.userName" placeholder="用户名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <!-- 设备名输入 -->
      <el-input v-model="queryParam.deviceName" placeholder="设备名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <!-- 一些按钮 -->
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleShowAll">
        显示全部
      </el-button>
      <el-button v-permission="['admin']" class="filter-item" style="margin-left: 10px;" type="danger" icon="el-icon-delete" @click="handleDeleteSome">
        批量删除
      </el-button>
      <!-- <el-button class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-edit" @click="handleReturnSome">
        批量还设备
      </el-button> -->
    </div>

    <!--数据表格-->
    <el-table
        ref="multipleTable"
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          fixed
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          fixed
          prop="borrowid"
          label="序号"
          width="100">
      </el-table-column>
      <el-table-column
          prop="userName"
          label="用户名"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="deviceName"
          label="设备名"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="borrowtimestr"
          label="租赁设备时间">
      </el-table-column>
      <el-table-column
          label="还设备时间">
          <template slot-scope="scope">
            <span v-if="scope.row.returntimestr === null || scope.row.returntimestr === ''" style="color: red">等待还设备</span>
            <span v-else style="color: #1aac1a">{{scope.row.returntimestr}}</span>
          </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          :width="roleIsAdmin?'180px':'110px'">
        <template slot-scope="scope">
          <el-button v-permission="['admin']" @click="handleDelete(scope.row,scope.$index)" type="danger" size="small">删除</el-button>
          <el-button v-if="!scope.row.returntimestr" @click="handleReturn(scope.row,scope.$index)" type="success" size="small">归还设备</el-button>
          <el-button v-else @click="handleComment(scope.row)" type="primary" size="small">去评价</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页条-->
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="queryParam.page"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="queryParam.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="recordTotal"
        style="margin-top: 15px">
    </el-pagination>

    <!-- 评价弹窗 -->
    <el-dialog title="设备评价" :visible.sync="dialogCommentVisible" width="40%">
      <el-form :model="commentForm" :rules="commentRules" ref="commentForm">
        <el-form-item label="评价内容" prop="commentText">
          <el-input type="textarea" v-model="commentForm.commentText" rows="4" placeholder="请输入评价内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCommentVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitComment">提交评价</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import permission from '@/directive/permission/index.js' // 权限判断指令
import waves from '@/directive/waves' // waves directive
import { getCount,queryBorrows,queryBorrowsByPage,addBorrow,deleteBorrow,deleteBorrows,updateBorrow,returnDevice } from '@/api/borrow'
import { addComment } from '@/api/comment'

export default {
  name: 'Deviceinfo',
  directives: { waves, permission },
  // 创建后
  created() {
    // 从服务器获取数据表格第一页的信息
    queryBorrowsByPage(this.queryParam).then(res => {
      console.log('首页数据获取成功',res)
      this.tableData = res.data
      this.recordTotal = res.count
    })
  },
  methods: {
    // 分页大小改变监听
    handleSizeChange(curSize) {
      const params = this.queryParam
      params.limit = curSize
      queryBorrowsByPage(params).then(res => {
            console.log('分页数据获取成功',res)
            this.tableData = res.data
            this.recordTotal = res.count
      })
    },

    // 点击分页监听方法
    handleCurrentChange(curPage) {
      const params = this.queryParam
      params.page = curPage
      queryBorrowsByPage(params).then(res => {
            console.log('分页数据获取成功',res)
            this.tableData = res.data
            this.recordTotal = res.count
      })
    },

    // 搜索
    handleFilter() {
      this.queryParam.page = 1
      queryBorrowsByPage(this.queryParam).then(res => {
        if(res.code === 0) {
          this.tableData = res.data
          this.recordTotal = res.count
        }
      })
    },

    // 显示全部
    handleShowAll() {
      this.queryParam.page = 1
      this.queryParam.userName = null
      this.queryParam.deviceName = null
      queryBorrowsByPage(this.queryParam).then(res => {
        if(res.code === 0) {
          this.tableData = res.data
          this.recordTotal = res.count
        }
      })
    },

    // 删除记录
    handleDelete(row, index) {
      this.$confirm('确定要删除该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBorrow(row).then(res => {
          if(res === 1) {
            this.$message.success('删除记录成功')
            this.tableData.splice(index, 1)
            // 如果删完了，获取上一页
            if(this.tableData.length === 0) {
              this.queryParam.page = this.queryParam.page - 1
              this.handleCurrentChange(this.queryParam.page)
            }
          } else {
            this.$message.error('删除记录失败')
          }
        })
      })
    },

    // 删除一些
    handleDeleteSome() {
      this.$confirm('确定要删除这些记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 获取选中的对象数组
        const items = this.$refs.multipleTable.selection
        deleteBorrows(items).then(res => {
          if(res > 0) {
            this.$message.success('删除' + res + '条记录成功')
            if(this.tableData.length === res) {  //如果本页内容全部删光了
              //当前页为上一页
              if(this.queryParam.page !== 0) {
                this.queryParam.page = this.queryParam.page - 1
              }
            }
            // 重载当前页
            this.handleCurrentChange(this.queryParam.page)
          } else {
            this.$message.error('删除记录失败')
          }
        })
      })
    },

    // 还设备
    handleReturn(row, index) {
      this.$confirm('确定要还设备吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        returnDevice(row.borrowid, row.deviceId).then(res => {
          if(res === 1) {
          this.$message.success('归还设备成功')
          this.handleCurrentChange(this.queryParam.page)
        } else {
          this.$message.error('归还设备失败')
        }
        })
      })
    },

    // 批量还设备
    // handleReturn(row, index) {
    //   this.$confirm('确定要还设备吗?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     returnBook(row.borrowid, row.bookid).then(res => {
    //       if(res === 1) {
    //         this.$message.success('还设备成功')
    //         this.handleCurrentChange(this.queryParam.page)
    //       } else {
    //         this.$message.error('还设备失败')
    //       }
    //     })
    //   })
    // },

    // 去评价
    handleComment(row) {
      this.commentForm = {
        borrowId: row.borrowid,
        deviceId: row.deviceId,
        commentText: ''
      }
      this.dialogCommentVisible = true
    },

    // 提交评价
    handleSubmitComment() {
      this.$refs.commentForm.validate(valid => {
        if (valid) {
          addComment({ commentId: null, borrowId: this.commentForm.borrowId, deviceId: this.commentForm.deviceId, commentText: this.commentForm.commentText }).then(res => {
            if (res === 1) {
              this.$message.success('评价提交成功')
              this.dialogCommentVisible = false
              this.handleCurrentChange(this.queryParam.page)
            } else {
              this.$message.error('评价提交失败')
            }
          })
        }
      })
    },

  },
  data() {
    return {
      // 表格数据
      tableData: [],
      // 记录总数
      recordTotal: 0,
      // 查询参数
      queryParam: {
        page: 1,
        limit: 10,
        userId: null,
        userName: null,
        deviceName: null,
      },
      // 评价弹窗显示状态
      dialogCommentVisible: false,
      // 评价表单数据
      commentForm: {
        borrowId: null,
        deviceId: null,
        commentText: ''
      },
      // 评价表单验证规则
      commentRules: {
        commentText: [
          { required: true, message: '请输入评价内容', trigger: 'blur' },
          { min: 5, max: 500, message: '评价内容长度在 5 到 500 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    // 获得user信息
    ...mapGetters(['id','name','roles']),
    roleIsAdmin() {
      if(this.roles[0] === 'admin') return true
      else return false
    }
  },
  watch: {
    'queryParam.userId': {
      immediate: true,
      handler() {
        console.log("我被触发了")
        if (this.roleIsAdmin) {
          this.queryParam.userId = null
        } else {
          this.queryParam.userId = this.id
        }
      }
    }
  }
}

</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 150px;
    height: 200px;
    display: block;
  }
</style>
