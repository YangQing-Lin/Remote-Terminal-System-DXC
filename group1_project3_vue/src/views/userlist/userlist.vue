<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.username"
            placeholder="用户名"
            clearable
          ></el-input>
          <el-col :span="8">
            <div class="block" style="display: flex"></div>
          </el-col>
          <el-button
            @click="getUserList"
            type="primary"
            round
            icon="el-icon-search"
            >查询</el-button
          >
          <el-button
            @click="DelUserList"
            type="danger"
            round
            icon="el-icon-search"
            >批量删除</el-button
          >
        </el-col>
        <el-col :span="4" align="right">
          <el-button
            @click="openEditUI(null)"
            type="primary"
            icon="el-icon-plus"
            circle
          ></el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 结果列表 -->
    <el-card>
      <el-table
        :data="userList"
        stripe
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column label="#" width="80">
          <template slot-scope="scope">
            <!-- (pageNo-1) * pageSize + index + 1 -->
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="url" label="头像" width="80">
          <template slot-scope="scope">
            <img
              width="40px"
              :src="scope.row.url"
              v-show="scope.row.url.split('//').length > 1"
            />
            <label v-show="scope.row.url.split('//').length <= 1">无头像</label>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名"> </el-table-column>
        <el-table-column prop="phone" label="角色名称">
          <template slot-scope="scope">
            <div
              class="tag"
              v-for="(tag, index) in getRole(scope.row.roleIdList)"
              :key="index"
            >
              <el-tag type="info">{{ tag }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱"> </el-table-column>
        <el-table-column prop="city" label="城市"> </el-table-column>
        <el-table-column prop="statue" label="用户状态" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.statue == 1" type="success">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="180">
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button
              @click="openEditUI(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              size="mini"
              circle
            ></el-button>
            <el-button
              @click="deleteUser(scope.row)"
              type="danger"
              icon="el-icon-delete"
              size="mini"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <!-- 用户信息编辑对话框 -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="userForm" ref="userFormRef" :rules="rules">
        <el-form-item label="头像" :label-width="formLabelWidth">
          <el-input v-model="userForm.url" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="userForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="账号"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          v-if="userForm.id == null || userForm.id == undefined"
          label="登录密码"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input
            type="password"
            v-model="userForm.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth">
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <!-- <el-form-item label="用户状态" :label-width="formLabelWidth">
          <el-switch
            v-model="userForm.statue"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item> -->

        <el-form-item label="用户角色" :label-width="formLabelWidth">
          <el-checkbox-group
            style="width: 85%"
            v-model="userForm.roleIdList"
            :max="2"
          >
            <el-checkbox
              v-for="role in roleList"
              :label="role.roleId"
              :key="role.roleId"
              >{{ role.roleDesc }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>

        <el-form-item
          label="电子邮件"
          prop="email"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import roleApi from "@/api/roleManage";
import userApi from "@/api/userManage";
import router from "@/router";
import store from "../../store";

export default {
  data() {
    var checkEmail = (rule, value, callback) => {
      var reg =
        /^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*\.[a-z]{2,}$/;
      if (!reg.test(value)) {
        return callback(new Error("邮箱格式错误"));
      }
      callback();
    };
    return {
      roleList: [],
      formLabelWidth: "130px",
      userForm: {
        roleIdList: [],
      },
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      userList: [],
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 50,
            message: "长度在 3 到 50 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入登录初始密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "长度在 6 到 16 个字符",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入电子邮件", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" },
        ],
      },
      multipleSelection: [],
    };
  },
  methods: {
    getAllRoleList() {
      roleApi.getAllRoleList().then((response) => {
        this.roleList = response.data;
        console.log(this.roleList);
      });
    },
    getRole(role) {
      if (role.length <= 0) {
        return ["未分配"];
      }
      let roles = [];
      role.forEach((item) => {
        this.roleList.forEach((list) => {
          if (item === list.roleId) {
            roles.push(list.roleDesc);
          }
        });
      });
      return roles;
    },
    DelUserList() {
      this.$confirm(`您确认删除用户这些选中用户吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.multipleSelection.forEach(item => {
            userApi.deleteUserById(item.id).then((response) => {
              this.$message({
                type: "success",
                message: response.message,
              });
              this.getUserList();
            })
          })
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    deleteUser(user) {
      this.$confirm(`您确认删除用户 ${user.username} ?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          userApi.deleteUserById(user.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getUserList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    saveUser() {
      // 触发表单验证
      this.$refs.userFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          userApi.saveUser(this.userForm).then((response) => {
            console.log("save user:", this.userForm);
            // 成功提示
            this.$message({
              message: response.message,
              type: "success",
            });
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格
            this.getUserList();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    clearForm() {
      this.userForm = {
        roleIdList: [],
      };
      this.$refs.userFormRef.clearValidate();
    },
    openEditUI(id) {
      if (id == null) {
        this.title = "新增用户";
      } else {
        this.title = "修改用户";
        // 根据id查询用户数据
        userApi.getUserById(id).then((response) => {
          this.userForm = response.data;
          console.log("data: ", response.data);
        });
      }
      this.dialogFormVisible = true;
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getUserList();
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getUserList();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val);
    },
    getUserList() {
      this.userList = []
      userApi.getUserList(this.searchModel).then((response) => {
        response.data.rows.forEach((item) => {
          userApi.getUserById(item.id).then((response2) => {
            this.userList.push(response2.data);
          });
        });
        console.log(this.userList);
        this.total = response.data.total;
      });
    },
  },
  created() {
    this.getUserList();
    this.getAllRoleList();
  },
};
</script>

<style>
#search .el-input {
  width: 200px;
  margin-right: 10px;
}
.el-dialog .el-input {
  width: 85%;
}
.tag {
  display: inline-block;
}
</style>
