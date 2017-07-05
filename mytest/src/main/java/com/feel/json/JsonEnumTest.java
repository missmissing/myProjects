package com.feel.json;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yulong.li on 2017/6/21.
 */
public class JsonEnumTest {
    public static void main(String[] args) {

        System.out.println(new Gson().toJson(ArticleType.CHINESE_TYPE));
    }

    enum ArticleType{
        @SerializedName("1")
        MATH_TYPE(1,"math"),
        @SerializedName("2")
        CHINESE_TYPE(2,"chinese")
        ;
        private int type;
        private String desc;

        ArticleType(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }

    }
}
