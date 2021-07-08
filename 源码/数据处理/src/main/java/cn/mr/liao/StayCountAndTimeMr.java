package cn.mr.liao;

import cn.mr.liao.utils.MyUtil;
import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 求在地方待的次数
 * 数据输入格式：
 * wid,lat,lon,mac,range,rssi,ds,startTime,endTime
 * 数据输出格式：
 * wid,mac,count
 */
public class StayCountAndTimeMr {

    /**
     * 求在地方待的次数
     * 数据输出格式：
     * wid,mac,count
     */

    public static class StayCountMapper extends Mapper<LongWritable, Text, Text,IntWritable> {

        Text text = new Text();
        IntWritable intWritable = new IntWritable();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);

            String wid = value.toString().split(",")[0];
            String mac = value.toString().split(",")[3];
            text.set(wid + "," + mac);
            intWritable.set(1);
            context.write(text, intWritable);
        }
    }

    public static class StayCountReducer extends Reducer<Text,IntWritable, Text,NullWritable> {
        Text text = new Text();
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);

            int count = 0;
            for (IntWritable v :
                    values) {
                count += v.get();
            }
            text.set(key.toString() + "," + count);
            context.write(text, NullWritable.get());
        }
    }

    /**
     * 求在地方待的时间
     * 数据输出格式：
     * wid,mac,totalTime
     */
    public static class StayTimeMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        Text text = new Text();
        IntWritable intWritable = new IntWritable();
        @SneakyThrows
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);

            String wid = value.toString().split(",")[0];
            String mac = value.toString().split(",")[3];
            text.set(wid + "," + mac);
            String startTime = value.toString().split(",")[7];
            String endTime = value.toString().split(",")[8];
            int minute = MyUtil.getApartMinute(startTime, endTime, "yyyyMMddHHmmss");
            intWritable.set(minute);
            context.write(text, intWritable);
        }
    }

    public static class StayTimeReucer extends Reducer<Text, IntWritable, Text, NullWritable> {

        Text text= new Text();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);

            int count = 0;
            for (IntWritable value :
                    values) {
                count += value.get();
            }
            String textStr = key.toString() + "," + count;
            text.set(textStr);
            context.write(text, NullWritable.get());
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        String[] inPaths = new String[]{"hdfs://localhost:9000/weblog/wifidata/*"};
        String outPath = "hdfs://localhost:9000/out/widsplit";
        Configuration conf = new Configuration();
        String jobCountName = "stayCount";
        JobInitModel jobCount = JobInitModel.builder()
                .inPaths(inPaths)
                .outPath(outPath)
                .conf(conf)
                .job(null)
                .jobName(jobCountName)
                .jarClass(StayCountAndTimeMr.class)
                .inputFormatClass(null)
                .mapper(StayCountMapper.class)
                .mapOutKeyClass(Text.class)
                .mapOutValueClass(IntWritable.class)
                .partitionnerClass(null)
                .numberReduceTask(null)
                .combinerClass(null)
                .reducer(StayCountReducer.class)
                .reduceOutKeyClass(Text.class)
                .reduceOutValueClass(NullWritable.class)
                .build();

        String jobTimeName = "stayTime";
        JobInitModel jobTime = JobInitModel.builder()
                .inPaths(inPaths)
                .outPath(outPath)
                .conf(conf)
                .job(null)
                .jobName(jobTimeName)
                .jarClass(StayCountAndTimeMr.class)
                .inputFormatClass(null)
                .mapper(StayTimeMapper.class)
                .mapOutKeyClass(Text.class)
                .mapOutValueClass(IntWritable.class)
                .partitionnerClass(null)
                .numberReduceTask(null)
                .combinerClass(null)
                .reducer(StayTimeReucer.class)
                .reduceOutKeyClass(Text.class)
                .reduceOutValueClass(NullWritable.class)
                .build();

        BaseDriver.initJob(new JobInitModel[]{jobCount, jobTime});
    }

}
