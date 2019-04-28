package cn.edu.entity;

import java.io.Serializable;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 16:01
 */
public class AddrTimeBean implements Serializable{
    private Integer auto_id;
    private String mac;
    private String addr;
    private Integer total_time;
    private Integer go_times;

    public AddrTimeBean() {
    }

    public AddrTimeBean(Integer auto_id, String mac, String addr, Integer total_time, Integer go_times) {
        this.auto_id = auto_id;
        this.mac = mac;
        this.addr = addr;
        this.total_time = total_time;
        this.go_times = go_times;
    }

    public Integer getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(Integer auto_id) {
        this.auto_id = auto_id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getTotal_time() {
        return total_time;
    }

    public void setTotal_time(Integer total_time) {
        this.total_time = total_time;
    }

    public Integer getGo_times() {
        return go_times;
    }

    public void setGo_times(Integer go_times) {
        this.go_times = go_times;
    }

    @Override
    public String toString() {
        return "AddrTimeBean{" +
                "auto_id=" + auto_id +
                ", mac='" + mac + '\'' +
                ", addr='" + addr + '\'' +
                ", total_time=" + total_time +
                ", go_times=" + go_times +
                '}';
    }
}
