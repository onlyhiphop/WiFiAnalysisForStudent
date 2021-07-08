package cn.mr.liao;

import cn.mr.liao.model.WifiData;
import cn.mr.liao.utils.MysqlUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;

/**
 * 将不同的wid写入不同文件夹中
 * 数据格式：
 * wid,lat,lon,time,mac,range,rssi,ds
 */
public class WidSplitMr {

    private static HashMap<String,String> map;

    static {
        map = MysqlUtil.getWidId();
    }

    public static class WidMapper extends Mapper<LongWritable, Text, Text, WifiData> {

        Text text = new Text();
        private WifiData wifiData = new WifiData();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);

            wifiData.parse(value.toString().split(","));
            text.set(wifiData.getWid());
            context.write(text, wifiData);
        }
    }

    public static class WidReducer extends Reducer<Text, WifiData, WifiData, NullWritable> {


        @Override
        protected void reduce(Text key, Iterable<WifiData> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);

            for (WifiData wd :
                    values) {
                context.write(wd, NullWritable.get());
            }
        }
    }

    /**
     * 根据wid分区
     * 注意：自定义分区的数量需要和reduce task的数量保持一致。
     */
    public static class WidPartitioner<K,V> extends Partitioner<K,V> {

        @Override
        public int getPartition(K key, V value, int numPartitions) {
            String wid = key.toString();
            int id = Integer.parseInt(map.get(wid));
            return id;
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        String[] inPaths = new String[]{"hdfs://localhost:9000/weblog/wifidata/*"};
        String outPath = "hdfs://localhost:9000/out/widsplit";
        Configuration conf = new Configuration();
        String jobName = "widsplit";
        JobInitModel jobInitModel = JobInitModel.builder()
                .inPaths(inPaths)
                .outPath(outPath)
                .conf(conf)
                .job(null)
                .jobName(jobName)
                .jarClass(WidSplitMr.class)
                .inputFormatClass(null)
                .mapper(WidMapper.class)
                .mapOutKeyClass(Text.class)
                .mapOutValueClass(WifiData.class)
                .partitionnerClass(WidPartitioner.class)
                .numberReduceTask(Integer.parseInt(map.get("count")))
                .combinerClass(null)
                .reducer(WidReducer.class)
                .reduceOutKeyClass(WifiData.class)
                .reduceOutValueClass(NullWritable.class)
                .build();
        BaseDriver.initJob(new JobInitModel[]{jobInitModel});
    }
}

