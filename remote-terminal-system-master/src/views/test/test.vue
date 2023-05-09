<template>
  <div class="test">
    <el-button type="primary" @click="openTerminal1">打开终端0</el-button>
    <el-button type="primary" @click="openTerminal2">打开终端1</el-button>
  </div>
</template>

<script>
import router from '@/router'
export default {
  data(){
    return{
      index: 1,
    }
  },
  methods: {
    openTerminal1() {
      let terminalInfo = {
          name: '测试1',
          ip: "116.205.237.77",
          port: 223,
          username: "test",
          password: "3.1415926",
        }
      let command = 'help'
      this.$router.push({name:'terminal', query:{method:'add', terminalInfo: terminalInfo, command: command}})
    },
    openTerminal2() {
      let terminalInfo = {
          name: '测试2',
          ip: "116.205.237.77",
          port: 221,
          username: "test",
          password: "888888",
        }
      let command = 'ls'
      this.$router.push({name:'terminal', query:{method:'add', terminalInfo: terminalInfo, command: command}})
    }
  }
}
</script>

<style>

</style>