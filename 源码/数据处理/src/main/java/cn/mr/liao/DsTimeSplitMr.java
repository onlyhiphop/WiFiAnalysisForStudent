package cn.mr.liao;

import cn.mr.liao.utils.MyUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 将手机息屏在夜间 startTime和endTime 切分变成 整点，求出次数
 * 数据输入格式：
 * wid,lat,lon,mac,range,rssi,ds,startTime,endTime
 */
public class DsTimeSplitMr {

    public static class DsTimeSplitMapper extends Mapper<LongWritable, Text,Text,IntWritable> {

        Text text = new Text();
        IntWritable iw = new IntWritable();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);

            String[] split = value.toString().split(",");
            String mac = split[3];

            for(int i = 7; i < 9; i ++){
                String time = MyUtil.timeToHour(split[i]);
                text.set("mac" + "," + time);
                iw.set(1);
                context.write(text,iw);
            }
        }
    }

    public static class DsTimeSplitReducer extends Reducer<Text, IntWritable, Text, NullWritable>{

        Text text = new Text();
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);
            int count = 0;
            for (IntWritable i :
                    values) {
                count += 1;
            }
            text.set(key + "," + count);
            context.write(text, NullWritable.get());
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        String[] inPaths = new String[]{"hdfs://localhost:9000/weblog/wifidata/*"};
        String outPath = "hdfs://localhost:9000/out/widsplit";
        Configuration conf = new Configuration();
        String jobName = "dsTimeSplit";
        JobInitModel jobInitModel = JobInitModel.builder()
                .inPaths(inPaths)
                .outPath(outPath)
                .conf(conf)
                .job(null)
                .jobName(jobName)
                .jarClass(DsTimeSplitMr.class)
                .inputFormatClass(null)
                .mapper(DsTimeSplitMapper.class)
                .mapOutKeyClass(Text.class)
                .mapOutValueClass(IntWritable.class)
                .partitionnerClass(null)
                .numberReduceTask(null)
                .combinerClass(null)
                .reducer(DsTimeSplitReducer.class)
                .reduceOutKeyClass(Text.class)
                .reduceOutValueClass(NullWritable.class)
                .build();
        BaseDriver.initJob(new JobInitModel[]{jobInitModel});
    }

}
