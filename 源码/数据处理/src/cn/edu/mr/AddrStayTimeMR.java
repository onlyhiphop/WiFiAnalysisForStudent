package cn.edu.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.hash.Hash;

import cn.edu.utils.TimeFormatUtil;

/**
 * 
* @Description: 
*	得到一个学生在某个地点的停留时间段和停留时间
*	输出：mac,addr,startTime,endTime,differMinute
* @version: v1.0.0
* @author: liao
* @date: 2019年1月27日 下午7:13:58 
 */
public class AddrStayTimeMR extends Configured implements Tool{
	
	private static ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
	
	public static class StayTimeMapper extends Mapper<LongWritable, Text, Text, NullWritable>{
		
		private  ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
		private String mac;
		
		private HashMap<String, String> hashMap = new HashMap<>();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			//输入：mac,range,time,addr,ds
			String line = value.toString();
			String[] splits = line.split(",");
			mac = splits[0];
			String time = splits[2];
			String addr = splits[3];
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put(time, addr);
			arrayList.add(hashMap);
		}
		
		@Override
		protected void cleanup(Mapper<LongWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			//用来存储时间对的下标
			ArrayList<String> abList = new ArrayList<>();
			for (int i = 0;i < arrayList.size(); i++) {
				HashMap<String, String> hashMap = arrayList.get(i);
				String addr = hashMap.values().toString();
				for (int j = i+1; j <= arrayList.size(); j++) {
					String addr2;
					if (j == arrayList.size()) {
						addr2 = "last";
					}else{
						HashMap<String, String> hashMap2 = arrayList.get(j);
						addr2 = hashMap2.values().toString();
					}
					if (!addr2.equals(addr)) {
						abList.add(i+"-"+(j-1));
						i = j - 1;
						break;
					}
				}
			}
			
			for (String str : abList) {
				int startTimeIndex = Integer.parseInt(str.split("-")[0]);
				int endTimeIndex = Integer.parseInt(str.split("-")[1]);
				HashMap<String, String> startMap = arrayList.get(startTimeIndex);
				HashMap<String, String> endMap = arrayList.get(endTimeIndex);
				String startTime = startMap.keySet().toString().replace("[", "").replace("]","");
				String endTime = endMap.keySet().toString().replace("[", "").replace("]","");
				String addr = endMap.values().toString().replace("[", "").replace("]","");
				int differMinute = TimeFormatUtil.getMinute(endTime) - TimeFormatUtil.getMinute(startTime);
				//大于10分钟才算在某个地点停留
				if (differMinute >= 10) {
					StringBuffer sb = new StringBuffer();
					sb.append(mac + "," + addr + "," + startTime + "," + endTime + "," + differMinute);
					context.write(new Text(sb.toString()),NullWritable.get());
				}
			}
		}
		
	}
	
	public static class StayTimeReducer extends Reducer<Text, Text, Text, NullWritable>{
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
	}
	
	@Override
	public int run(String[] arg0) throws Exception {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance();
		
		job.setJarByClass(AddrStayTimeMR.class);
		
		job.setMapperClass(StayTimeMapper.class);
		job.setReducerClass(StayTimeReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		Path inputpath = new Path(arg0[0]);
		Path outputpath = new Path(arg0[1]);

		FileSystem fs = FileSystem.get(configuration);
		if(fs.exists(outputpath)){
			fs.delete(outputpath,true);
		}
		
		FileInputFormat.setInputPaths(job, inputpath);
		FileOutputFormat.setOutputPath(job, outputpath);
		
		return job.waitForCompletion(true) ? 1 : 0;
	}
	
	public static void main(String[] args) throws Exception {
		if (args == null || args.length < 2) {
			System.err.println("参数错误，使用参数：输入路径 + 输出路径");
			System.exit(-1);
		}
		int res  = ToolRunner.run(new Configuration(), new AddrStayTimeMR(),args);
		System.exit(res);
	}
}
