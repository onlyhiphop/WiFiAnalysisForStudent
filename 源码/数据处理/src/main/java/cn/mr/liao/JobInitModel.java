package cn.mr.liao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;

/**
 * 用于BaseDriver初始化job的需求(多个job的情况下)
 * @author liaobaocai
 * @date 2020/3/14
 * @Email 760003259@qq.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobInitModel {
    private String[] inPaths;   //程序的输入目录
    private String outPath;     //程序的输出目录
    private Configuration conf; //配置信息
    private Job job;            //Job相关设置，如分布式文件缓存共享等
    private String jobName;     //该job的名称
    private Class<?> jarClass;  //mr的驱动程序类
    private Class<? extends InputFormat> inputFormatClass;  //输入格式化类
    private Class<? extends Mapper> mapper; //mapper的实现类
    private Class<?> mapOutKeyClass;    //mapper输出的key的类型
    private Class<?> mapOutValueClass;  //mapper输出的value类型
    private Class<? extends Reducer> combinerClass;
    private Class<? extends Partitioner> partitionnerClass;
    private Integer numberReduceTask;  //设置reduce task的数量
    private Class<? extends Reducer> reducer;   //reducer的实现类
    private Class<?> reduceOutKeyClass; //reduce输出的key的类型
    private Class<?> reduceOutValueClass;   //reduce输出的value类型

}
