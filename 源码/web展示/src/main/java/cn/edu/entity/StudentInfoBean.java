package cn.edu.entity;

import java.io.Serializable;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 14:22
 */
public class StudentInfoBean implements Serializable{

    private String id;
    private String mac;
    private String name;
    private String phone;

    public StudentInfoBean() {
    }

    public StudentInfoBean(String id, String mac, String name, String phone) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "StudentInfoBean{" +
                "id='" + id + '\'' +
                ", mac='" + mac + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
