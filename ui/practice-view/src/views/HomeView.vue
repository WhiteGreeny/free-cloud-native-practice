<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import sha1 from 'crypto-js/sha1';
interface LoginDTO {
  username: string,
  password: string
}
const emptyLoginDTO:LoginDTO = {
  username:'',
  password:''
}
function createLoginDTO(data: Partial<LoginDTO> = {}): LoginDTO {
    return {
        ...emptyLoginDTO,
        ...data
    }
}
const sth = ref(0);
const loginForm = ref<LoginDTO>(createLoginDTO());
const token = ref('');
const TOKEN_PREFIX = 'Bearer ';
const showLogin = ref(true);
const message = ref('');
const messageStyle = ref('success-msg');
const showMessage = ref(false);
const currentUsername = ref('');
function handleLogin() {
  sth.value++;
  let sha1Pwd = sha1(loginForm.value.password).toString();
  fetch('/api/auth/login',{
    method: 'POST',
    body:JSON.stringify({
      username:loginForm.value.username,
      password:sha1Pwd
    })
  }).then((res)=>res.json())
  .then((body)=>{
    if(body.code==200){
      token.value=body.data;
      showLogin.value=false;
      renderMessage('登录成功','success');
      getInfo();
    }else{
      renderMessage(body.message,'error');
    }
    
  });
}
function getInfo(){
  if(token.value != undefined && token.value.length>0){
    fetch('/api/demo/getInfo',{
      headers:{
        Authorization: TOKEN_PREFIX+token.value
      }
    }).then((res)=>res.json())
    .then((body)=>{
      if(body.code == 200){
        currentUsername.value=body.data.username
      }else{
        renderMessage(body.message,'error');
      }
    })
  }
}
function renderMessage(str:string,type:string){
  message.value = str;
  if(type ==='success'){
    messageStyle.value = 'succcess-msg';
  }else{
    messageStyle.value = 'error-msg';
  }
  showMessage.value = true;
  let taskId = setInterval(()=>{
    showMessage.value = false;
    clearInterval(taskId);
  },3000)
}
</script>

<template>
  <main>
    <text :class="[messageStyle]" v-if="showMessage">{{ message }}</text>
    <div v-if="showLogin">
      <div>
        <input type="text" v-model="loginForm.username" placeholder="用户名"/>
        <input type="password" v-model="loginForm.password" placeholder="密码"/>
      </div>
      <button @click="handleLogin">登录</button>
    </div>
    <div v-if="!showLogin">
      <text>hello,{{ currentUsername }}</text>
    </div>
  </main>
</template>
<style scoped lang="css">
.error-msg {
  color: red;
}
.success-msg {
  color: green;
}
</style>