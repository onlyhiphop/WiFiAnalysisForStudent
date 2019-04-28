package cn.edu.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

import cn.edu.utils.TimeFormatUtil;

//继承Writable实现序列化
public class MacBean implements WritableComparable<MacBean>{

	private String mac;
	private double range;
	private String time;
	private String addr;
	private String ds;
	
	//空构造器：用于反序列化
	public MacBean() {
		// TODO Auto-generated constructor stub
	}
	

	public MacBean(String mac, double range, String time, String addr, String ds) {
		super();
		this.mac = mac;
		this.range = range;
		this.time = time;
		this.addr = addr;
		this.ds = ds;
	}

	//生成get、set方法
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setDs(String ds){
		this.ds = ds;
	}

	public String getDs() {
		return ds;
	}


	//要有toString方法，它才能写成一个文本文件
	@Override
	public String toString() {
		return mac + "," + range + "," + time + "," + addr + "," + ds;
	}

	//从数据流中反序列化出对象的数据
	@Override
	public void readFields(DataInput input) throws IOException {

		mac = input.readUTF();
		range = input.readDouble();
		time = input.readUTF();
		addr = input.readUTF();
		ds = input.readUTF();
	}



	//序列化对象到数据流中
	@Override
	public void write(DataOutput output) throws IOException {

		output.writeUTF(mac);
		output.writeDouble(range);
		output.writeUTF(time);
		output.writeUTF(addr);
		output.writeUTF(ds);
	}


	@Override
	public int compareTo(MacBean o) {
		Long t1 = TimeFormatUtil.getFormatTime(o.getTime());
		Long t2 = TimeFormatUtil.getFormatTime(time);
		return t2 > t1 ? 1 : -1;
	}
	
}
