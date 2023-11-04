const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    port: 8082, // 设置端口号
    proxy: {                 //设置代理，必须填
      '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
        target: 'http://localhost:8089',     //代理的目标地址
        changeOrigin: true,              //是否设置同源，输入是的
        pathRewrite:{'^/api': ''},//路径改写
        ws:true
      }
    }

  },
  transpileDependencies: true,

})