package com.huowolf.single;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description 通过枚举方式实现单例（简单优雅，推荐）
 * 自动支持序列化机制，防止反序列化重新创建新的对象
 */
public enum  Singleton {

    INSTANCE;

    public void otherMethods(){
        System.out.println("something");
    }


    public static void main(String[] args) {
        Singleton.INSTANCE.otherMethods();
    }
}


