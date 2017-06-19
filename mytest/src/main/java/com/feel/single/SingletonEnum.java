package com.feel.single;

/**
 * Created by lyan on 2017/3/6.
 */
public enum SingletonEnum {
    INSTANCE;

    private Resource instance;

    SingletonEnum(){
        instance = new Resource();
    }

    public Resource getInstance() {
        return instance;
    }

}

class Resource{}
