<template>
    <div>
      <el-button @click="openEditUI2()" class="mainb" type="primary">新增</el-button>
  
      <el-table
        class="tablea"
        :data="menuList"
        style="width: 97%; margin-bottom: 20px"
        row-key="menuId"
        border
        default-expand-all
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column prop="title" label="名称" width="120"> </el-table-column>
        <el-table-column prop="redirect" label="权限编码" width="120"> </el-table-column>
        <el-table-column prop="menuId" label="id" width="120"> </el-table-column>
        <el-table-column prop="icon" label="图标" width="120"> </el-table-column>
        <el-table-column prop="type" label="类型" width="120"> 
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type == 0">目录</el-tag>
            <el-tag type="success" v-if="scope.row.type == 1">菜单</el-tag>
            <el-tag type="info" v-if="scope.row.type == 2">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="菜单url" width="120">
        </el-table-column>
        <el-table-column prop="component" label="菜单组件" width="120">
        </el-table-column>
        <el-table-column prop="type" label="排序号" width="120">
        </el-table-column>
        <el-table-column prop="statue" label="状态" width="120">
          <template slot-scope="scope">
              <el-tag v-if="scope.row.statue == 0">正常</el-tag>
              <el-tag v-else type="danger">禁用</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
          <el-button @click="openEditUI(scope.row)" type="text">编辑</el-button>
          <el-button @click="deleteMenuById(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    <el-dialog
      @close="clearForm"
      :title="title"
      :visible.sync="dialogFormVisible"
      :before-close="handleClose"
    >
      <el-form :model="menuForm" ref="menuForm" >
        <el-form-item
          label="名称"
          prop="title"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="权限编码"
          prop="redirect"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm.redirect" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="图标"
          prop="icon"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="菜单url"
          prop="path"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="菜单组件"
          prop="component"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm.component" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="父级id"
          prop="parentId"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm.parentId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="状态"
          prop="statue"
          :label-width="formLabelWidth"
        >
        <el-radio-group v-model="menuForm.statue">
          <el-radio :label="0">正常</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
      


    <el-dialog
        @close="clearForm2"
        :visible.sync="dialogFormVisible2"
        :before-close="handleClose"
      >
      <el-form :model="menuForm2" ref="menuForm2" >
      <el-form-item
          label="名称"
          prop="title"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm2.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="权限编码"
          prop="redirect"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm2.redirect" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="父级id"
          prop="parentId"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm2.parentId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="图标"
          prop="icon"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm2.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="菜单url"
          prop="path"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm2.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="菜单组件"
          prop="component"
          :label-width="formLabelWidth"
        >
          <el-input v-model="menuForm2.component" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="类型"
          prop="type"
          :label-width="formLabelWidth"
        >
        <el-radio-group v-model="menuForm2.type">
          <el-radio :label="0">目录</el-radio>
          <el-radio :label="1">菜单</el-radio>
          <el-radio :label="2">按钮</el-radio>
        </el-radio-group>
        </el-form-item>
        <el-form-item
          label="状态"
          prop="statue"
          :label-width="formLabelWidth"
        >
        <el-radio-group v-model="menuForm2.statue">
          <el-radio :label="0">正常</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
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
  import menuApi from "@/api/menuManage";
  export default {
    data() {
      return {
        dialogFormVisible:false,
        dialogFormVisible2:false,
        formLabelWidth: "130px",
        title: "",
        menuList: [],
        menuForm:{
          component:'',
          icon:'',
          menuId:'',
          name:'',
          parentId:'',
          redirect:'',
          path:'',
          title:'',
          type:'',
          statue:'',
          isLeaf:'',
        },
        menuForm2:{
          hidden:false
        },
      };
    },
    methods: {
      getAllMenu() {
        menuApi.getAllMenu().then((response) => {
          this.menuList = response.data;
          console.log(response.data)
        });
        
      },


      save() {
        // 触发表单验证
        if(this.menuForm.title!==''&&this.menuForm.parentId!==''&&this.menuForm.redirect!==''&&this.menuForm.icon!==''&&this.menuForm.statue!==''&&this.menuForm.type!==''&&this.menuForm.path!==''&&this.menuForm.component!==''){
          menuApi.updateMenu(this.menuForm).then(res=>{
            this.getAllMenu();
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

      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },

      clearForm() {
        this.menuForm = {
          component:'',
          icon:'',
          menuId:'',
          name:'',
          parentId:'',
          redirect:'',
          path:'',
          title:'',
          type:'',
          statue:'',
          isLeaf:''
        };
        this.$refs.menuForm.clearValidate();
      },

      clearForm2() {
        this.menuForm2 = {};
        this.$refs.menuForm2.clearValidate();
      },

    openEditUI(value) {
        console.log(value)
        this.menuForm.hidden=false
        this.menuForm.component=value.component
        this.menuForm.name=value.name
        this.menuForm.icon=value.icon
        this.menuForm.menuId=value.menuId
        this.menuForm.parentId=value.parentId
        this.menuForm.redirect=value.redirect
        this.menuForm.path=value.path
        this.menuForm.title=value.title
        this.menuForm.type=value.type
        this.menuForm.statue=value.statue
        this.menuForm.children=value.children
        this.menuForm.isLeaf=value.isLeaf
        this.dialogFormVisible = true;
    },

    openEditUI2() {
        this.dialogFormVisible2 = true;
    },

    add(){
        if(this.menuForm2.title!==''&&this.menuForm2.parentId!==''&&this.menuForm2.redirect!==''&&this.menuForm2.icon!==''&&this.menuForm2.statue!==''&&this.menuForm2.type!==''&&this.menuForm2.path!==''&&this.menuForm2.component!==''){
          menuApi.addMenu(this.menuForm2).then(res=>{
            this.getAllMenu();
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

    deleteMenuById(menu){
      this.$confirm(`您确认删除用户 ${menu.title} ?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          menuApi.deleteMenuById(menu.menuId).then(response => {
            this.$message({
              type: 'success',
              message: response.message
            });
            this.getAllMenu();
          });
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    }


    },

    
    created() {
      this.getAllMenu();
    },
  };
  </script>
  <style>
  .mainb {
    margin-left: 20px;
    margin-top: 20px;
  }
  .tablea {
    margin-left: 20px;
    margin-top: 20px;
    margin-right: 20px;
    border: 1px solid #000;
  }
  </style>