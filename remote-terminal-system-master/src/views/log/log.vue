<template>
    <div>
      <el-row class="b1">
        <el-col :span="4">
          <el-input class="pd" v-model="name" placeholder="名称" clearable></el-input>
        </el-col>
        <el-col :span="4">
          <el-input class="pd" v-model="server" placeholder="部署服务器" clearable></el-input>
        </el-col>
        <el-col :span="2">
          <el-button  type="primary" @click="search()">查询</el-button>
        </el-col>
        <el-col :span="2">
          <el-button @click="reset()">重置</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary"  @click="openEditUI2()" plain>添加</el-button>
        </el-col>
      </el-row>
  
      <el-table
      class="tablea"
        :data="logList"
        style="width: 97%" border>
        <el-table-column
          prop="name"
          label="名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="server"
          label="部署服务器"
          width="180">
        </el-table-column>
        <el-table-column
          prop="ip"
          label="主机"
          width="180">
        </el-table-column>
        <el-table-column
          prop="port"
          label="部署端口">
        </el-table-column>
        <el-table-column
          prop="command"
          label="命令">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button @click="openTerminal(scope.row)" type="text">连接</el-button>
            <el-button @click="openEditUI(scope.row)" type="text">编辑</el-button>
            <el-button @click="deleteConv(scope.row)" type="text">删除</el-button>
          </template>
          
        </el-table-column>
      </el-table>

      <!-- <el-pagination
    class="bt"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="40">
    </el-pagination> -->

    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
      :before-close="handleClose"
    >
      <el-form :model="logForm" ref="logForm" >
        <el-form-item
          label="名称"
          prop="name"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="主机"
          prop="ip"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm.ip" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="部署服务器"
          prop="server"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm.server" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="部署端口"
          prop="port"
          :label-width="formLabelWidth"
        >
        <el-input v-model="logForm.port" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="用户名"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label=" 命令"
          prop="command"
          :label-width="formLabelWidth"
        >
        <el-input v-model="logForm.command" autocomplete="off"></el-input>
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
      <el-form :model="logForm2" ref="convForm" >
        <el-form-item
          label="名称"
          prop="name"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm2.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="部署服务器"
          prop="server"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm2.server" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="主机"
          prop="ip"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm2.ip" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="部署端口"
          prop="port"
          :label-width="formLabelWidth"
        >
        <el-input v-model="logForm2.port" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="命令"
          prop="command"
          :label-width="formLabelWidth"
        >
        <el-input v-model="logForm2.command" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="用户名"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm2.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input v-model="logForm2.password" autocomplete="off"></el-input>
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
  import logApi from "@/api/LogManage";
  export default {
    data() {
      return {
        dialogFormVisible: false,
        dialogFormVisible2:false,
        name: "",
        ip: "",
        server:'',
        title:'',
        formLabelWidth: "130px",
        logList:[],
        logForm:{
          username:'',
          id:'',
          name:'',
          server:'',
          ip:'',
          port:'',
          command:'',
          password:''
        },
        logForm2:{},
      };
    },
    methods: {
    getLogList(){
      logApi.getLogList().then((response) => {
        this.logList = response.data;
        console.log(this.logList)
      });
    },

    openTerminal(row) {
      let terminalInfo =
        {
          name: row.name,
          ip: row.ip,
          port: row.port,
          username: row.username,
          password: row.password,
        }
        
      let command = row.command
      this.$router.push({name:'terminal', query:{method:'add', terminalInfo: terminalInfo, command: command}})
},




    async search() {
        let ilist=[]
        let a = await logApi.getLogList();
        if(this.name&&!this.server){
          console.log(111)
          a.data.forEach((item) => {
            if(item.name.includes(this.name)){
              ilist.push(item)
            }
          })
          this.logList=ilist
          // this.convList = filerReasource;
        }else if(this.server&&!this.name){
          console.log(222)
          a.data.forEach((item) => {
            if(item.server.includes(this.server)){
              ilist.push(item)
            }
          })
          this.logList=ilist
        }else if(this.server&&this.name){
          console.log(333)
          a.data.forEach((item) => {
            if(item.name.includes(this.name)&&item.server.includes(this.server)){
              ilist.push(item)
            }
          })
          this.logList=ilist
        } else{
          this.logList=a.data
        }  
    },

    async reset() {
        this.name = ''
        this.ip = ''
        let a = await logApi.getLogList();
        this.logList=a.data
        
    },

    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
    

    clearForm() {
      this.logForm = {
        id:'',
          name:'',
          server:'',
          ip:'',
          port:'',
          command:'',
          password:'',
          username:'',
      };
      this.$refs.logForm.clearValidate();
    },

    deleteConv(log) {
      this.$confirm(`您确认删除日志 ${log.name} ?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          logApi.deleteLogById(log.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getLogList();
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
      console.log(value)
      this.logForm.id=value.id
      this.logForm.username=value.username
      this.logForm.name=value.name
      this.logForm.port=value.port
      this.logForm.ip=value.ip
      this.logForm.server=value.server
      this.logForm.command=value.command
      this.logForm.password=value.password
      this.dialogFormVisible = true;
    },

    openEditUI2(){
      this.dialogFormVisible2 = true;
    },

    add(){
      if(this.logForm2.name!==''&&this.logForm2.ip!==''&&this.logForm2.username!==''&&this.logForm2.port!==''){
        logApi.addLog(this.logForm2).then(res=>{
          console.log(res)
          this.getLogList();
        })
        this.dialogFormVisible2=false
        
      }
      else{
        this.$message({
          message:'表单含有空信息',
          type:'warning'
        })
      }
    },

    save() {
      // 触发表单验证
      if(this.logForm.name!==''&&this.logForm.ip!==''&&this.logForm.command!==''&&this.logForm.port!==''){
        logApi.updateLog(this.logForm).then(res=>{
          this.getLogList();
        })
        this.dialogFormVisible=false
      }
      else{
        this.$message({
          message:'表单含有空信息',
          type:'warning'
        })
      }
    },
  },
  created() {
    this.getLogList();
  },
  };
  </script>
  
  <style>
  .b1{
    margin-left: 20px;
    margin-top: 20px;
  }
  .tablea{
      margin-left: 20px;
      margin-top: 20px;
  }
  .pd{
    padding-right:5px ;
  }
  .bt{
  margin-left: 20px;
}
  </style>