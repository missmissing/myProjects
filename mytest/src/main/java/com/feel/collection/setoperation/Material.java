package com.feel.collection.setoperation;

/**
 * Created by yulong.li on 2017/9/8.
 */
public class Material {

    private Integer id;

    private String code;

    private String desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Material(Integer id, String code, String desc) {
        this.id = id;
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "id: " + id + " code: " + code + " desc: " + desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Material material = (Material) o;

        return code.equals(material.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
