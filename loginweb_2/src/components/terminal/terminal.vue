<template>
    <div class="terminal-view" id="terminal-view">
        <div id="terminal" ref="terminal" v-if="getFlag(terminalInfo.is_show)" :name="terminalInfo.is_show"></div>
    </div>
</template>

<script>
export default {
    props: {
        terminalInfo: Object,
    },
    data() {
        return {
            terminal: {
                operate: "connect",
                host: this.terminalInfo.host,
                port: this.terminalInfo.port,
                username: this.terminalInfo.username,
                password: this.terminalInfo.password,
            },
            indication: "none",
        };
    },
    mounted() {
        this.openTerminal(this.terminal);
        console.log("$$$$$$$$$$$$$$$$");
        console.log(this.terminal);
        console.log(this.terminalInfo.is_show);
    },
    methods: {
        openTerminal(options) {
            let that = this;
            // console.log(options)
            var client = new WSSHClient();
            var term = new Terminal({
                cols: 150,
                rows: 40,
                cursorBlink: true, // 光标闪烁
                cursorStyle: "block", // 光标样式  null | 'block' | 'underline' | 'bar'
                scrollback: 800, //回滚
                tabStopWidth: 8, //制表宽度
                screenKeys: true,
            });

            term.on("data", function (data) {
                //键盘输入时的回调函数
                client.sendClientData(data);
            });
            term.open(document.getElementById("terminal"));
            //在页面上显示连接中...
            term.write("Connecting...");
            that.indication = "connecting";
            //执行连接操作
            client.connect({
                onError: function (error) {
                    //连接失败回调
                    term.write("Error: " + error + "\r\n");
                    that.$message.error("登录失败，请检查账号密码IP等");
                    that.indication = "error";
                    console.log(that.indication);
                },
                onConnect: function () {
                    //连接成功回调
                    client.sendInitData(options);
                    that.indication = "connecting";
                },
                onClose: function () {
                    //连接关闭回调
                    term.write("\rconnection closed");
                    that.indication = "close";
                },
                onData: function (data) {
                    //收到数据时回调
                    term.write(data);
                    that.indication = "success";
                },
            });
        },
        getFlag(message) {
            console.log("message:", message);
            return message;
        },
    },
};
</script>

<style scoped>
</style>