package cn.edu.entity;

import java.io.Serializable;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 16:07
 */
public class DsBean implements Serializable{

    private String mac;
    private Integer N_time;
    private Integer Y_time;
    private String active_time;
    private String laterN_time;

    public DsBean() {
    }

    public DsBean(String mac, Integer n_time, Integer y_time, String active_time, String laterN_time) {
        this.mac = mac;
        N_time = n_time;
        Y_time = y_time;
        this.active_time = active_time;
        this.laterN_time = laterN_time;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getN_time() {
        return N_time;
    }

    public void setN_time(Integer n_time) {
        N_time = n_time;
    }

    public Integer getY_time() {
        return Y_time;
    }

    public void setY_time(Integer y_time) {
        Y_time = y_time;
    }

    public String getActive_time() {
        return active_time;
    }

    public void setActive_time(String active_time) {
        this.active_time = active_time;
    }

    public String getLaterN_time() {
        return laterN_time;
    }

    public void setLaterN_time(String laterN_time) {
        this.laterN_time = laterN_time;
    }

    @Override
    public String toString() {
        return "DsBean{" +
                "mac='" + mac + '\'' +
                ", N_time=" + N_time +
                ", Y_time=" + Y_time +
                ", active_time='" + active_time + '\'' +
                ", laterN_time='" + laterN_time + '\'' +
                '}';
    }
}
