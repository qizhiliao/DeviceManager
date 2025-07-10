<template>
  <div class="app-container">
    <!-- 顶部功能 -->
    <div class="filter-container" style="margin-bottom: 15px">
      <!-- 设备名输入 -->
      <el-input v-model="queryParam.deviceName" placeholder="设备名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <!-- 品牌输入 -->
      <el-input v-model="queryParam.deviceAuthor" placeholder="品牌" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <!-- 类型选择 -->
      <el-select v-model="queryParam.deviceTypeId" filterable placeholder="类型" clearable class="filter-item" style="width: 200px">
        <el-option
            v-for="item in typeData"
            :key="item.deviceTypeId"
            :label="item.deviceTypeName"
            :value="item.deviceTypeId">
          </el-option>
      </el-select>
      <!-- 一些按钮 -->
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleShowAll">
        显示全部
      </el-button>
      <el-button v-permission="['admin']" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加设备
      </el-button>
      <el-button v-permission="['admin']" class="filter-item" style="margin-left: 10px;" type="danger" icon="el-icon-delete" @click="handleDeleteSome">
        批量删除
      </el-button>
    </div>

    <!--弹出框-->
    <el-dialog :title="formTitle" :visible.sync="dialogFormVisible" width="40%">
      <el-row>
        <el-col :span="16">
          <!--普通表单-->
          <el-form :model="form" :rules="rules" ref="ruleForm" label-width="80px">

            <el-form-item label="设备名称" prop="deviceName">
              <el-input v-model="form.deviceName"></el-input>
            </el-form-item>

            <el-form-item label="品牌" prop="deviceAuthor">
              <el-input v-model="form.deviceAuthor"></el-input>
            </el-form-item>

            <el-form-item label="价格" prop="devicePrice">
              <el-input v-model="form.devicePrice"></el-input>
            </el-form-item>

            <el-form-item label="设备类型" prop="deviceTypeId">
              <el-select v-model="form.deviceTypeId" placeholder="请选择类型">
                <el-option
                    v-for="item in typeData"
                    :key="item.deviceTypeId"
                    :label="item.deviceTypeName"
                    :value="item.deviceTypeId">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="设备描述" prop="deviceDesc">
              <el-input type="textarea" v-model="form.deviceDesc"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="8">
          <div align="center">
            <h3>点击下方图片上传封面</h3>
            <!--上传图片-->
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8092/DeviceManager/update/updateImg"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="form.deviceImg" :src="form.deviceImg" class="avatar" alt="封面无法显示">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>

        </el-col>
      </el-row>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!--弹出框2-->
    <el-dialog title="选择用户" :visible.sync="dialogFormVisible2" width="400px">
      <el-form :model="form2">
        <el-form-item label="用户名" prop="userId" label-width="80px">
              <el-select v-model="form2.deviceTypeId" placeholder="请选择用户">
                <el-option
                    v-for="item in userData"
                    :key="item.userId"
                    :label="item.userName"
                    :value="item.userId">
                </el-option>
              </el-select>
            </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="submitForm2">确 定</el-button>
      </div>
    </el-dialog>

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
          prop="deviceId"
          label="序号"
          width="100">
      </el-table-column>
      <el-table-column
          v-if="roleIsAdmin === false"
          label="设备封面"
          width="155">
          <template slot-scope="scope">
            <el-image :src="scope.row.deviceImg" style="width: 130px; height: 180px"></el-image>
          </template>
      </el-table-column>
      <el-table-column
          prop="deviceName"
          label="设备名称"
          width="150"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="deviceAuthor"
          label="设备品牌"
          width="100"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="devicePrice"
          label="设备价格"
          width="100">
      </el-table-column>
      <el-table-column
          prop="deviceTypeName"
          label="设备类型名"
          width="100"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="deviceDesc"
          label="设备描述"
          min-width="300"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          label="设备状态"
          width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.isBorrowed === 1" style="color: red">已借出</span>
            <span v-else style="color: #1aac1a">未借出</span>
          </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          :width="roleIsAdmin?'240px':'110px'">
        <template slot-scope="scope">
          <el-button v-permission="['admin']" @click="handleUpdate(scope.row)" type="primary" size="small">编辑</el-button>
          <el-button v-permission="['admin']" @click="handleDelete(scope.row,scope.$index)" type="danger" size="small">删除</el-button>
          <el-button @click="handleBorrow(scope.row)" type="success" size="small">借用设备</el-button>
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
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import permission from '@/directive/permission/index.js' // 权限判断指令
import waves from '@/directive/waves' // waves directive
import {
  getCount,
  queryDeviceInfosByPage,
  addDeviceInfo,
  deleteDeviceInfo,
  deleteDeviceInfos,
  updateDeviceInfo
} from '@/api/deviceinfo'
import { queryDeviceTypes } from '@/api/devicetype'
import { borrowDevice } from '@/api/borrow'
import { queryUsers } from '@/api/user'

export default {
  name: 'Deviceinfo',
  directives: { waves, permission },
  // 创建后
  created() {
    // 从服务器获取数据表格第一页的信息
    queryDeviceInfosByPage(this.queryParam).then(res => {
      console.log('首页数据获取成功',res)
      this.tableData = res.data
      this.recordTotal = res.count
    })
    // 从服务器获取所有的设备类型
    queryDeviceTypes().then(res => {
      console.log('设备类型获取成功',res)
      this.typeData = res
    })
  },
  mounted() {
    if (this.roleIsAdmin === false) {
      this.queryParam.limit = 5
      this.handleSizeChange(this.queryParam.limit)
    }
  },
  methods: {
    // 分页大小改变监听
    handleSizeChange(curSize) {
      const params = this.queryParam
      params.limit = curSize
      queryDeviceInfosByPage(params).then(res => {
            console.log('分页数据获取成功',res)
            this.tableData = res.data
            this.recordTotal = res.count
      })
    },

    // 点击分页监听方法
    handleCurrentChange(curPage) {
      const params = this.queryParam
      params.page = curPage
      queryDeviceInfosByPage(params).then(res => {
            console.log('分页数据获取成功',res)
            this.tableData = res.data
            this.recordTotal = res.count
      })
    },

    // 搜索设备
    handleFilter() {
      this.queryParam.page = 1
      console.log('查询参数:', this.queryParam);
      queryDeviceInfosByPage(this.queryParam).then(res => {
        if(res.code === 0) {
          this.tableData = res.data
          this.recordTotal = res.count
        }
      })
    },

    // 显示全部
    handleShowAll() {
      this.queryParam.page = 1
      this.queryParam.deviceName = null
      this.queryParam.deviceAuthor = null
      this.queryParam.deviceTypeId = null
      queryDeviceInfosByPage(this.queryParam).then(res => {
        if(res.code === 0) {
          this.tableData = res.data
          this.recordTotal = res.count
        }
      })
    },

    // 图片上传成功监听
    handleAvatarSuccess(res, file) {
      console.log(res)
      console.log(file)
      if(res.code === 0) {
        this.$message.success('上传成功');
        this.form.deviceImg = res.data
      } else {
        this.$message.error('上传失败，请联系管理员');
      }
    },

    // 图片上传之前监听
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传封面图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传封面图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    // 点击添加记录
    handleCreate() {
      // 从服务器获取所有的设备类型
      queryDeviceTypes().then(res => {
        console.log('设备类型获取成功',res)
        this.typeData = res
      })
      // 表单是添加状态
      this.formType = 0
      // 将空数据置入form
      this.form = {
        deviceId: null,
        deviceName: '',
        deviceAuthor: '',
        devicePrice: '',
        deviceTypeId: 1,
        deviceDesc: '',
        isBorrowed: 0,
        deviceImg: ''
      }
      // 显示表单框
      this.dialogFormVisible = true
    },

    // 点击编辑记录
    handleUpdate(row) {
      // 从服务器获取所有的设备类型
      queryDeviceTypes().then(res => {
        console.log('设备类型获取成功',res)
        this.typeData = res
      })
      // 表单是编辑状态
      this.formType = 1
      // 将行数据置入form
      this.form = {
        deviceId: row.deviceId,
        deviceName: row.deviceName,
        deviceAuthor: row.deviceAuthor,
        devicePrice: row.devicePrice,
        deviceTypeId: row.deviceTypeId,
        deviceDesc: row.deviceDesc,
        isBorrowed: row.isBorrowed,
        deviceImg: row.deviceImg
      }
      // 显示表单框
      this.dialogFormVisible = true
    },

    // 点击借用设备
    handleBorrow(row) {
      if (this.roleIsAdmin) {
        // 显示表单框
        this.dialogFormVisible2 = true
        // 获取设备信息
        this.form2.deviceId = row.deviceId

        // 获取用户信息
        queryUsers().then(res => {
          this.userData = res
        })
      } else {
        this.$confirm('您确定要借设备吗?', '提示').then(() => {
          borrowDevice(this.id, row.deviceId).then(res => {
            if(res === 1) {
              this.$message.success('借设备成功')
              this.handleCurrentChange(this.queryParam.page)
            } else {
              this.$message.error('借设备失败')
            }
            this.dialogFormVisible2 = false // 关闭对话框
          })
        })
      }
    },

    // 添加和更新的提交表单
    submitForm() {
      if (this.formType === 0) {  // 添加记录
        addDeviceInfo(this.form).then(res => {
          if(res === 1) {
            this.$message.success('添加记录成功')
            // 跳转到末尾
            getCount().then(res => {
              this.recordTotal = res
              this.queryParam.page = Math.ceil(this.recordTotal / this.queryParam.limit)
              this.handleCurrentChange(this.queryParam.page)
            })
          } else {
            this.$message.error('添加记录失败')
          }
          this.dialogFormVisible = false  // 关闭对话框
        })
      } else if(this.formType === 1) {  //更新记录
        updateDeviceInfo(this.form).then(res => {
          if(res === 1) {
            this.$message.success('更新记录成功')
            this.handleCurrentChange(this.queryParam.page)
          } else {
            this.$message.error('更新记录失败')
          }
          this.dialogFormVisible = false  // 关闭对话框
        })
      }
    },

    // 借设备的提交表单
    submitForm2() {
      borrowDevice(this.form2.userId, this.form2.deviceId).then(res => {
        if(res === 1) {
          this.$message.success('借设备成功')
          this.handleCurrentChange(this.queryParam.page)
        } else {
          this.$message.error('借设备失败')
        }
        this.dialogFormVisible2 = false // 关闭对话框
      })
    },

    // 删除记录
    handleDelete(row, index) {
      this.$confirm('确定要删除该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDeviceInfo(row).then(res => {
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
        deleteDeviceInfos(items).then(res => {
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

  },
  data() {
    return {
      // 表格数据
      tableData: [],
      // 记录总数
      recordTotal: 0,
      // 设备类型数据
      typeData: [],
      // 用户数据
      userData: [],
      // 查询参数
      queryParam: {
        page: 1,
        limit: 10,
        deviceName: null,
        deviceAuthor: null,
        deviceTypeId: null
      },
      // 对话框表单显示
      dialogFormVisible: false,
      dialogFormVisible2: false,
      // 表单类型（添加数据:0,修改数据:1）
      formType: 0,
      // 表单数据
      form: {
        deviceId: null,
        deviceName: '',
        deviceAuthor: '',
        devicePrice: 0,
        deviceTypeId: 1,
        deviceDesc: '',
        isBorrowed: 0,
        deviceImg: ''
      },
      form2: {
        userId: 1,
        deviceId: 1
      },
      rules: {
        deviceName: [
          { required: true, message: '请输入设备名称', trigger: 'blur' }
        ],
        deviceAuthor: [
          { required: true, message: '请输入品牌', trigger: 'blur' }
        ],
        devicePrice: [
          { required: true, message: '请输入价格', trigger: 'blur' }
        ],
        deviceTypeId: [
          { required: true, message: '请选择类型', trigger: 'blur' }
        ],
        deviceDesc: [
          { required: true, message: '请输入描述', trigger: 'blur' }
        ],
        isBorrowed: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ]
      },
    }
  },
  computed: {
    // 获得user信息
    ...mapGetters(['id','name','roles']),
    // 通过表单类型计算表单标题
    formTitle() {
      return this.formType === 0 ? '添加记录' : '修改记录'
    },
    roleIsAdmin() {
      if(this.roles[0] === 'admin') return true
      else return false
    },

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
