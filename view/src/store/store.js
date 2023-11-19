import * as pinia from "pinia";
import {ElMessage} from "element-plus";
import {ref} from "vue";

export const useUserInfoStore = pinia.defineStore("userinfo-store", () => {
    const tokenJson=ref("")
    const setToken = (token) => {
        try {
            sessionStorage.setItem("token", JSON.stringify(token));
            console.log(token)
            return JSON.parse(tokenJson.value||window.sessionStorage.getItem("user")||"{}")
        }catch (err){
            ElMessage.error("json字符串格式不对！！")
        }

    };

    function getToken  ()  {
        const userJSON = sessionStorage.getItem("token");
        if (userJSON) {
            return JSON.parse(userJSON);
        }
        return null;
    };

    const removeToken = () => {
        sessionStorage.removeItem("token");
        tokenJson.value=""
    };

    return {
        setToken,
        getToken,
        removeToken,
        tokenJson
    };
});