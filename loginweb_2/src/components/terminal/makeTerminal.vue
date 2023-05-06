<template>
  <div class="make-terminal">
    <div class="message">
      <mavon-editor class="markdown" :style="markdownView" :boxShadow="false" previewBackground="inherit" :toolbarsFlag="false" :editable="false" v-model="content" :subfield="false" defaultOpen="preview"></mavon-editor>
      <input class="input" style="outline:none;" @keyup.enter="submit">
    </div>
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
        operate:'connect',
        host: this.terminalInfo.host,
        port: this.terminalInfo.port,
        username: this.terminalInfo.username,
        password: this.terminalInfo.password,
      },
      indication: 'none',
      content: '',
    }
  },
  computed: {
    markdownView(){
      return 'background-color: inherit; border: 1px solid transparent; color: white;'
    },
  },
  mounted() {
    this.getMessage(this.terminal)
  },
  methods: {
    getMessage(options) {
      let that = this
      var client = new WSSHClient();
      client.connect({
        onError: function (error) {
          //连接失败回调
          that.content += 'Error: ' + error + '\r\n'
          that.$message.error('登录失败，请检查账号密码IP等')
          that.indication = 'error'
          console.log(that.indication)
        },
        onConnect: function () {
          //连接成功回调
          client.sendInitData(options);
          that.indication = 'connecting'
        },
        onClose: function () {
          //连接关闭回调
          that.content += "\rconnection closed"
          that.indication = 'close'
        },
        onData: function (data) {
          //收到数据时回调
          that.content = that.content + data
          console.log(that.content)
          that.indication = 'success'
        }
      })
    }
  }
}
</script>

<style scoped>
.make-terminal{
  width: 100%;
  height: 800px;
  background-color: black;
  color: white;
  overflow: scroll;
}
.message {
  width: 100%;
  height: 100%;
}
.markdown {
  display: block;
  width: 100%;
  height: 755px;
}
.input {
  height: 30px;
  line-height: 30px;
  width: 100%;
  border: 0;
  background-color: #cecece;
}
</style>