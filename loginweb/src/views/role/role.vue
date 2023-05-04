<template>
    <div>
        <!-- 搜索栏 -->
        <el-card id="search">
            <el-row>
                <el-col :span="20">
                    <el-input v-model="searchModel.roleName" placeholder="角色名称" clearable></el-input>
                    <el-button @click="getRoleList" type="primary" round icon="el-icon-search">查询</el-button>

                    <el-button @click="getConvList" type="primary" round icon="el-icon-search">获取会话列表</el-button>
                    <el-button @click="addConv" type="primary" round icon="el-icon-search">添加会话</el-button>
                    <el-button @click="updateConv" type="primary" round icon="el-icon-search">修改会话</el-button>
                    <el-button @click="deleteConv" type="primary" round icon="el-icon-search">删除会话</el-button>

                    <el-button @click="getLogList" type="primary" round icon="el-icon-search">获取日志列表</el-button>
                    <el-button @click="addLog" type="primary" round icon="el-icon-search">添加日志</el-button>
                    <el-button @click="updateLog" type="primary" round icon="el-icon-search">修改日志</el-button>
                    <el-button @click="deleteLog" type="primary" round icon="el-icon-search">删除日志</el-button>
                </el-col>
                <el-col :span="4" align="right">
                    <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" circle></el-button>
                </el-col>
            </el-row>
        </el-card>

        <!-- 结果列表 -->
        <el-card>
            <el-table :data="roleList" stripe style="width: 100%">
                <el-table-column label="#" width="80">
                    <template slot-scope="scope">
                        <!-- (pageNo-1) * pageSize + index + 1 -->
                        {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
                    </template>
                </el-table-column>
                <el-table-column prop="roleId" label="角色ID" width="200">
                </el-table-column>
                <el-table-column prop="roleName" label="角色名称" width="260">
                </el-table-column>
                <el-table-column prop="roleDesc" label="角色描述"> </el-table-column>
                <el-table-column label="操作" width="380">
                    <template slot-scope="scope">
                        <el-button @click="openEditUI(scope.row.roleId)" id="1" type="primary" icon="el-icon-edit" size="small">分配权限</el-button>
                        <el-button @click="openEditUI(scope.row.roleId)" type="primary" icon="el-icon-edit" size="mini" circle>编辑</el-button>
                        <el-button @click="deleteRole(scope.row)" type="danger" icon="el-icon-delete" size="mini" circle>删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 分页组件 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="searchModel.pageNo" :page-sizes="[5, 10, 20, 50]" :page-size="searchModel.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>

        <!-- 角色信息编辑对话框 -->
        <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="roleForm" ref="roleFormRef" :rules="rules">
                <el-form-item label="角色名称" prop="roleName" :label-width="formLabelWidth">
                    <el-input v-model="roleForm.roleName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色描述" prop="roleDesc" :label-width="formLabelWidth">
                    <el-input v-model="roleForm.roleDesc" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限设置" prop="menuIdList" :label-width="formLabelWidth">
                    <el-tree :data="menuList" :props="menuProps" show-checkbox default-expand-all node-key="menuId" ref="menuRef" style="width:85%"></el-tree>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRole">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import roleApi from "@/api/roleManage";
import menuApi from "@/api/menuManage";
import convApi from "@/api/ConversationManage";
import logApi from "@/api/LogManage";
export default {
    data() {
        return {
            menuList: [],
            menuProps: {
                children: "children",
                label: "title",
            },
            formLabelWidth: "130px",
            roleForm: {},
            dialogFormVisible: false,
            title: "",
            total: 0,
            searchModel: {
                pageNo: 1,
                pageSize: 10,
            },
            roleList: [],
            rules: {
                roleName: [
                    {
                        required: true,
                        message: "请输入角色名称",
                        trigger: "blur",
                    },
                    {
                        min: 2,
                        max: 20,
                        message: "长度在 2 到 20 个字符",
                        trigger: "blur",
                    },
                ],
                roleDesc: [
                    {
                        required: true,
                        message: "请输入角色描述",
                        trigger: "blur",
                    },
                    {
                        min: 2,
                        max: 20,
                        message: "长度在 2 到 20 个字符",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        getAllMenu() {
            menuApi.getAllMenu().then((response) => {
                this.menuList = response.data;
            });
        },
        deleteRole(role) {
            this.$confirm(`您确认删除角色 ${role.roleName} ?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    roleApi.deleteRoleById(role.roleId).then((response) => {
                        this.$message({
                            type: "success",
                            message: response.message,
                        });
                        this.getRoleList();
                    });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除",
                    });
                });
        },
        saveRole() {
            // 触发表单验证
            this.$refs.roleFormRef.validate((valid) => {
                if (valid) {
                    let checkedKeys = this.$refs.menuRef.getCheckedKeys();
                    let halfCheckedKeys =
                        this.$refs.menuRef.getHalfCheckedKeys();
                    this.roleForm.menuIdList =
                        checkedKeys.concat(halfCheckedKeys);
                    // 提交请求给后台
                    roleApi.saveRole(this.roleForm).then((response) => {
                        // 成功提示
                        this.$message({
                            message: response.message,
                            type: "success",
                        });
                        // 关闭对话框
                        this.dialogFormVisible = false;
                        // 刷新表格
                        this.getRoleList();
                    });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },
        clearForm() {
            this.roleForm = {};
            this.$refs.roleFormRef.clearValidate();
            this.$refs.menuRef.setCheckedKeys([]);
        },
        openEditUI(id) {
            if (id == null) {
                this.title = "新增角色";
            } else {
                this.title = "修改角色";
                // 根据id查询角色数据
                roleApi.getRoleById(id).then((response) => {
                    this.roleForm = response.data;
                    this.$refs.menuRef.setCheckedKeys(response.data.menuIdList);
                });
            }
            this.dialogFormVisible = true;
        },
        handleSizeChange(pageSize) {
            this.searchModel.pageSize = pageSize;
            this.getRoleList();
        },
        handleCurrentChange(pageNo) {
            this.searchModel.pageNo = pageNo;
            this.getRoleList();
        },
        getRoleList() {
            roleApi.getRoleList(this.searchModel).then((response) => {
                this.roleList = response.data.rows;
                this.total = response.data.total;
            });
        },

        // 调用会话API
        getConvList() {
            convApi.getConvList().then((response) => {
                console.log("getConvList:", response);
            });
        },
        addConv() {
            let conversation = {
                name: "测试账号",
                ip: "1.1.1.1",
                port: 22,
                username: "test",
                password: "test",
            };
            convApi.addConv(conversation).then((response) => {
                console.log("addConv:", response);
            });
        },
        updateConv() {
            let conversation = {
                id: 4,
                name: "测试账号update",
                ip: "1.1.1.1",
                port: 22,
                username: "test",
                password: "test",
            };
            convApi.updateConv(conversation).then((response) => {
                console.log("updateConv:", response);
            });
        },
        deleteConv() {
            let id = 4;
            convApi.deleteConvById(id).then((response) => {
                console.log("deleteConvById:", response);
            });
        },

        // 调用日志API
        getLogList() {
            logApi.getLogList().then((response) => {
                console.log("getLogList:", response);
            });
        },
        addLog() {
            let log = {
                name: "测试日志",
                server: "服务器",
                ip: "116.205.237.77",
                port: 223,
                command: "tail -n 10 /home/test/access.log",
                password: "3.1415926",
            };
            logApi.addLog(log).then((response) => {
                console.log("addLog:", response);
            });
        },
        updateLog() {
            let log = {
                id: 6,
                name: "测试日志update",
                server: "服务器",
                ip: "116.205.237.77",
                port: 223,
                command: "tail -n 10 /home/test/access.log",
                password: "3.1415926",
            };
            logApi.updateLog(log).then((response) => {
                console.log("updateLog:", response);
            });
        },
        deleteLog() {
            let id = 6;
            logApi.deleteLogById(id).then((response) => {
                console.log("deleteLogById:", response);
            });
        },
    },

    created() {
        this.getRoleList();
        this.getAllMenu();
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
</style>