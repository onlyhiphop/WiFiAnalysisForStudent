package cn.mr.liao.model;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 数据实体类 wid,lat,lon,time,mac,range,rssi,ds
 * @author liaobaocai
 * @date 2020/3/15
 */
public class WifiData implements WritableComparable<WifiData> {

    private String wid;     //wifi探针id
    private String lat;     //纬度
    private String lon;     //经度
    private String time;    //时间
    private String mac;     //手机的mac地址
    private String range;   //范围
    private String rssi;    //手机的信号强度
    private String ds;      //是否息屏，Y是 ""为否

    /**
     * 用于反序列化
     */
    public WifiData() {
    }

    public WifiData(String wid, String lat, String lon, String time, String mac, String range, String rssi, String ds) {
        this.wid = wid;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.mac = mac;
        this.range = range;
        this.rssi = rssi;
        this.ds = ds;
    }

    /**
     * 按时间进行排序: 从小到大排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(WifiData o) {
        long t1 = Long.parseLong(this.time);
        long t2 = Long.parseLong(o.getTime());
        return t1 > t2 ? 1 : -1;
    }

    /**
     * 序列化对象到数据流中
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(wid);
        out.writeUTF(lat);
        out.writeUTF(lon);
        out.writeUTF( time);
        out.writeUTF(mac);
        out.writeUTF(range);
        out.writeUTF(rssi);
        out.writeUTF(ds);
    }

    /**
     * 从数据流中反序列化出对象的数据
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        wid = in.readUTF();
        lat = in.readUTF();
        lon = in.readUTF();
        time = in.readUTF();
        mac = in.readUTF();
        range = in.readUTF();
        rssi = in.readUTF();
        ds = in.readUTF();
    }

    /**
     * 要有toString方法，才能写成一个文本文件
     * @return
     */
    @Override
    public String toString() {
        return wid + "," +
                lat + "," +
                lon + "," +
                time + "," +
                mac + "," +
                range + "," +
                rssi + "," +
                ds;
    }

    /**
     * 自定义方法 封装WifiData
     * @param split 一行切分的数据
     * @return
     */
    public void parse(String[] split){
        this.wid = split[0];
        this.lat = split[1];
        this.lon = split[2];
        this.time = split[3];
        this.mac = split[4];
        this.range = split[5];
        this.rssi = split[6];
        this.ds = split[7];
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }
}
