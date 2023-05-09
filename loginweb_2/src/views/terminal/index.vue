<template>
    <div class="terminal-view">
        <div style="margin-bottom: 20px;">
            <el-button @click="addTab(editableTabsValue)">
                add tab
            </el-button>
        </div>
        <!-- <make-terminal :terminalInfo="editableTabs[0].content"></make-terminal> -->
        <el-tabs v-model="editableTabsValue" type="card" @tab-click="handleClick" closable @tab-remove="removeTab">
            <!-- <el-tab-pane v-for="(item, index) in editableTabs" :key="index" :label="item.title" :name="item.name">
                <div class="test_div"></div> -->
            <terminal ref="terminal" :terminalInfo="editableTabs[0].content"></terminal>
            <terminal ref="terminal" :terminalInfo="editableTabs[0].content"></terminal>
            <!-- </el-tab-pane> -->
        </el-tabs>
        <button @click="test">test</button>
    </div>
</template>

<script>
import terminal from "@/components/terminal/terminal.vue";
// import MakeTerminal from "@/components/terminal/makeTerminal.vue";
export default {
    components: { terminal },
    data() {
        return {
            editableTabsValue: "0",
            editableTabs: [
                {
                    title: "Tab 0",
                    name: "0",
                    content: {
                        is_show: 1,
                        host: "116.205.237.77",
                        port: 223,
                        username: "test",
                        password: "3.1415926",
                    },
                },
                {
                    title: "Tab 1",
                    name: "1",
                    content: {
                        is_show: 0,
                        host: "116.205.237.77",
                        port: 223,
                        username: "test",
                        password: "3.1415926",
                    },
                },
                {
                    title: "Tab 3",
                    name: "3",
                    content: {
                        is_show: 0,
                        host: "116.205.237.77",
                        port: 223,
                        username: "test",
                        password: "3.1415926",
                    },
                },
            ],
            tabIndex: 2,
            onTab: 1,
            isTerminal: false,
        };
    },
    mounted() {
        this.init();
    },
    computed: {
        colorIndication() {
            let indication = this.$refs.terminal[0].indication;
            switch (indication) {
                case "connecting":
                    return "🟡";
                case "success":
                    return "🟢";
                case "error":
                    return "❌";
                case "close":
                    return "🔴";
                default:
                    return "⚪";
            }
        },
    },
    methods: {
        test() {
            let terminalInfo = {
                name: "测试",
                ip: "116.205.237.77",
                port: 223,
                username: "test",
                password: "3.1415926",
            };
            // this.addTab(terminalInfo);
        },
        init() {
            console.log(this.$route.query);
            if (this.$route.query.method === "add") {
                // this.addTab(this.$route.query.terminalInfo);
            }
            this.getIndication();
        },
        getIndication() {
            setTimeout(() => {
                this.editableTabs[0].title = this.colorIndication;
                this.getIndication();
            }, 200);
        },
        addTab(tabInfo) {
            this.isTerminal = true;
            let newTabName = ++this.tabIndex + "";
            this.editableTabs.push({
                title: tabInfo.name,
                name: this.editableTabs.length + 1 + "",
                content: {
                    host: tabInfo.ip,
                    port: tabInfo.port,
                    username: tabInfo.username,
                    password: tabInfo.password,
                },
            });
            this.editableTabsValue = newTabName;
        },
        removeTab(targetName) {
            let tabs = this.editableTabs;
            let activeName = this.editableTabsValue;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }

            this.editableTabsValue = activeName;
            this.editableTabs = tabs.filter((tab) => tab.name !== targetName);
        },
        handleClick(tab, event) {
            this.isTerminal = false;
        },
        getFlag(item_name, index) {
            console.log("##########");
            console.log(item_name);
            return item_name;
        },
        my_cons(message) {
            console.log("%%%%%%%%%%%");
            console.log(message);
            console.log(typeof message);
        },
    },
};
</script>

<style scoped>
.test_div {
    width: 20px;
    height: 20px;
    background-color: brown;
}
</style>