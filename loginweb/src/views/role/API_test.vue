<template>
    <div>
        <el-button @click="getConvList" type="primary" round icon="el-icon-search">获取会话列表</el-button>
        <el-button @click="addConv" type="primary" round icon="el-icon-search">添加会话</el-button>
        <el-button @click="updateConv" type="primary" round icon="el-icon-search">修改会话</el-button>
        <el-button @click="deleteConv" type="primary" round icon="el-icon-search">删除会话</el-button>

        <el-button @click="getLogList" type="primary" round icon="el-icon-search">获取日志列表</el-button>
        <el-button @click="addLog" type="primary" round icon="el-icon-search">添加日志</el-button>
        <el-button @click="updateLog" type="primary" round icon="el-icon-search">修改日志</el-button>
        <el-button @click="deleteLog" type="primary" round icon="el-icon-search">删除日志</el-button>
    </div>
</template>

<script>
import convApi from "@/api/ConversationManage";
import logApi from "@/api/LogManage";
export default {
    data() {
        return {};
    },
    methods: {
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

    created() {},
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