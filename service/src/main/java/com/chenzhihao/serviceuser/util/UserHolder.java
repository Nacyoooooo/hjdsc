package com.chenzhihao.serviceuser.util;


import com.chenzhihao.serviceuser.model.Users;

//Threadlocal工具类
public class UserHolder {
    private static final ThreadLocal<Users> tl = new ThreadLocal<>();

    public static void saveUser(Users user){
        tl.set(user);
    }

    public static Users getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
