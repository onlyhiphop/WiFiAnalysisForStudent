package cn.edu.mr;

import java.io.IOException;

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
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cn.edu.domain.MacBean;

/**
 * 
* @Description: 
*	对一个学生的文件中的信息，按时间排序
*	输出： mac,range,time,addr,ds
* @version: v1.0.0
* @author: liao
* @date: 2019年1月26日 下午3:34:15 
 */
public class TimeSortMR extends Configured implements Tool{

	public static class TimeSortMapper extends Mapper<LongWritable, Text, MacBean, NullWritable>{
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, MacBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] splits = line.split(",");
			String mac = splits[0];
			Double range = Double.parseDouble(splits[1]);
			String time = splits[2];
			String addr = splits[3];
			String ds = splits[4];
			MacBean macBean = new MacBean(mac, range, time, addr, ds);
			context.write(macBean, NullWritable.get());
		}
	}
	
	public static class TimeSortReducer extends Reducer<MacBean, NullWritable, MacBean, NullWritable>{
		@Override
		protected void reduce(MacBean key, Iterable<NullWritable> values,
				Reducer<MacBean, NullWritable, MacBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
	}
	
	@Override
	public int run(String[] arg0) throws Exception {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		
		job.setJarByClass(TimeSortMR.class);

		job.setMapperClass(TimeSortMapper.class);
		job.setReducerClass(TimeSortReducer.class);
		
		LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
		
		job.setOutputKeyClass(MacBean.class);
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
		int res  = ToolRunner.run(new Configuration(), new TimeSortMR(),args);
		System.exit(res);
	}
}
