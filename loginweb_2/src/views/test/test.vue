<template>
  <div class="test">
    <el-button type="primary" @click="openTerminal()">打开终端</el-button>
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
    openTerminal() {
      let terminalInfo = {
        name: '测试',
        ip: "116.205.237.77",
        port: 223,
        username: "test",
        password: "3.1415926",
      }
      this.$router.push({name:'terminal', query:{method:'add', terminalInfo: terminalInfo}})
    }
  }
}
</script>

<style>

</style>