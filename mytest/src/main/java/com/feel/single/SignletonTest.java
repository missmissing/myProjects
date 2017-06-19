package com.feel.single;

/**
 * Created by lyan on 2017/3/6.
 */
public class SignletonTest {
    private static SignletonTest signletonTest;

    private SignletonTest(){}

    static {
        signletonTest = new SignletonTest();
    }

    public SignletonTest getSignleton(){
        return signletonTest;
    }


}
