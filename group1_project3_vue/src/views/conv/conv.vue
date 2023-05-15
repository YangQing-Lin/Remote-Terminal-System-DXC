<template>
  <div>
    <el-row class="b1">
      <el-col :span="4">
        <el-input
          class="pd"
          v-model="name"
          placeholder="名称"
          clearable
        ></el-input>
      </el-col>
      <el-col :span="4">
        <el-input
          class="pd"
          v-model="ip"
          placeholder="主机"
          clearable
        ></el-input>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" @click="search()">查询</el-button>
      </el-col>
      <el-col :span="2">
        <el-button @click="reset()">重置</el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" @click="openEditUI2()" plain>添加</el-button>
      </el-col>
    </el-row>

    <el-table class="tablea" :data="convList" stripe style="width: 97%" border>
      <el-table-column prop="name" label="名称" width="180"> </el-table-column>
      <el-table-column prop="id" label="id" width="180" hidden>
      </el-table-column>
      <el-table-column prop="ip" label="主机" width="180"> </el-table-column>
      <el-table-column prop="port" label="端口"> </el-table-column>
      <el-table-column prop="username" label="用户名"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="openTerminal(scope.row)" type="text"
            >连接</el-button
          >
          <el-button @click="openEditUI(scope.row)" type="text">编辑</el-button>
          <el-button @click="deleteConv(scope.row)" type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--   
      <el-pagination
      class="bt"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="searchModel.pageNo"
        :page-sizes="[5, 20, 30, 40]"
        :page-size="searchModel.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
  
   -->
    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
      :before-close="handleClose"
    >
      <el-form :model="convForm" ref="convForm">
        <el-form-item label="名称" prop="name" :label-width="formLabelWidth">
          <el-input v-model="convForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主机" prop="ip" :label-width="formLabelWidth">
          <el-input v-model="convForm.ip" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="端口" prop="port" :label-width="formLabelWidth">
          <el-input v-model="convForm.port" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="用户名"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="convForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input v-model="convForm.password" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible2"
      :before-close="handleClose"
    >
      <el-form :model="convForm2" ref="convForm2">
        <el-form-item label="名称" prop="name" :label-width="formLabelWidth">
          <el-input v-model="convForm2.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主机" prop="ip" :label-width="formLabelWidth">
          <el-input v-model="convForm2.ip" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="端口" prop="port" :label-width="formLabelWidth">
          <el-input v-model="convForm2.port" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="用户名"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="convForm2.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input v-model="convForm2.password" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
  <script>
import convApi from "@/api/ConversationManage";
export default {
  data() {
    return {
      dialogFormVisible: false,
      dialogFormVisible2: false,
      title: "",
      name: "",
      ip: "",
      total: "",
      formLabelWidth: "130px",
      searchModel: {
        pageNo: 1,
        pageSize: 5,
      },
      convForm: {
        id:'',
        name:'',
        ip:'',
        port:'',
        username:'',
        password:''
      },
      convForm2: {},
      convList: [],
    };
  },
  methods: {
    getConvList() {
      convApi.getConvList().then((response) => {
        this.convList = response.data;
        console.log(this.convList);
      });
    },

    // getList(){
    //   convApi.getList(this.searchModel).then((response) => {
    //     this.convList = response.data;
    //     this.total=response.data.total
    //     console.log(this.convList)
    //   });
    // },

    // handleSizeChange(pageSize) {
    //   this.searchModel.pageSize = pageSize;
    //   this.getList();
    // },
    // handleCurrentChange(pageNo) {
    //   this.searchModel.pageNo = pageNo;
    //   this.getList();
    // },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },

    openTerminal(row) {
      let terminalInfo = {
        name: row.name,
        ip: row.ip,
        port: row.port,
        username: row.username,
        password: row.password,
      };
      this.$router.push({
        name: "terminal",
        query: { method: "add", terminalInfo: terminalInfo },
      });
    },

    async search() {
      let ilist = [];
      let a = await convApi.getConvList();
      if (this.name && !this.ip) {
        console.log(111);
        a.data.forEach((item) => {
          if (item.name.includes(this.name)) {
            ilist.push(item);
          }
        });
        this.convList = ilist;
        // this.convList = filerReasource;
      } else if (this.ip && !this.name) {
        console.log(222);
        a.data.forEach((item) => {
          if (item.ip === this.ip) {
            ilist.push(item);
          }
        });
        this.convList = ilist;
      } else if (this.ip && this.name) {
        console.log(333);
        a.data.forEach((item) => {
          if (item.name.includes(this.name) && item.ip === this.ip) {
            ilist.push(item);
          }
        });
        this.convList = ilist;
      } else {
        this.convList = a.data;
      }
    },

    async reset() {
      this.name = "";
      this.ip = "";
      let a = await convApi.getConvList();
      this.convList = a.data;
    },

    clearForm() {
      this.convForm = {
        id:'',
        name:'',
        ip:'',
        port:'',
        username:'',
        password:''
      };
      this.$refs.convForm.clearValidate();
    },

    deleteConv(conv) {
      this.$confirm(`您确认删除会话 ${conv.name} ?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          convApi.deleteConvById(conv.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getConvList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    openEditUI(value) {
      this.convForm.id=value.id;
      this.convForm.name = value.name;
      this.convForm.ip = value.ip;
      this.convForm.port = value.port;
      this.convForm.username = value.username;
      this.convForm.password = value.password;
      console.log(value.name);
      this.dialogFormVisible = true;
    },

    openEditUI2() {
      this.dialogFormVisible2 = true;
    },

    add() {
      if (
        this.convForm2.name !== "" &&
        this.convForm2.ip !== "" &&
        this.convForm2.username !== "" &&
        this.convForm2.port !== ""
      ) {
        convApi.addConv(this.convForm2).then(res=>{
           this.getConvList();
        });
        this.dialogFormVisible2 = false;
       
       
        
        
      } else {
        this.$message({
          message: "表单含有空信息",
          type: "warning",
        });
      }
    },

    save() {
      // 触发表单验证
      if (
        this.convForm.name !== "" &&
        this.convForm.ip !== "" &&
        this.convForm.username !== "" &&
        this.convForm.port !== ""
      ) {
        console.log(this.convForm)
        convApi.updateConv(this.convForm).then(res=>{
          console.log(res)
          this.getConvList();
        });
        this.dialogFormVisible = false;
        
      } else {
        this.$message({
          message: "表单含有空信息",
          type: "warning",
        });
      }
    },
  },
  created() {
    this.getConvList();
    // this.getList()
  },
};
</script>
  
  <style>
.b1 {
  margin-left: 20px;
  margin-top: 20px;
}
.tablea {
  margin-left: 20px;
  margin-top: 20px;
}
.pd {
  padding-right: 5px;
}
.bt {
  margin-left: 20px;
}
</style>