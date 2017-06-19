package com.feel.single;

/**
 * Created by lyan on 2017/3/6.
 */
public class SignletonTest2 {
    private static SignletonTest2 signletonTest;

    private SignletonTest2(){}

    public static SignletonTest2 getInstance(){
        if(signletonTest == null){
            signletonTest = new SignletonTest2();
        }
        return signletonTest;
    }

}
