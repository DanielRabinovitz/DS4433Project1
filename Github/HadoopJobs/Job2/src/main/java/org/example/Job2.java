package org.example;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.*;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//This file is meant to do the following hadoop job:
//Find the 8 pages with the most visits, the return page ID, Name, and Nationality for each of the top 8 pages
public class Job2{

    //Job (map only) to get the records for each of the top 8 items

    public static void main(String[] args) throws Exception {

        String[] input = new String[4];
        //get data from here
        input[0] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1CSVs\\AccessLog.csv";
        input[1] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1CSVs\\MyPage.csv";
        //output mapreduce job to here
        input[2] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1Outputs\\job2output1";
        input[3] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1Outputs\\job2output2";
        //program throws an error if the output folder exists, so delete the folder
        FileUtils.deleteDirectory(new File(input[2]));
        FileUtils.deleteDirectory(new File(input[3]));

        Configuration conf = new Configuration();
        Configuration conf2 = new Configuration();
        Job job = Job.getInstance(conf, "Find top 8 pages");
        Job job2 = Job.getInstance(conf2, "get records for top 8 IDs");
        //sets the things to run for map and reduce
        job.setJarByClass(Job2.class);
        job.setMapperClass(LogMapper.class);
        job.setReducerClass(LogReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(input[0]));
        FileOutputFormat.setOutputPath(job, new Path(input[2]));
        job.waitForCompletion(true);

        //doesn't work right now
        //@TODO figure out what down here is wrong
        //need to convert the filepath for the output of the first job to URI format
        File cache_file = new File(input[2]+"\\part-r-00000");

        job2.setJarByClass(Job2.class);
        job2.setMapperClass(PageMapper.class);
        //job2.setReducerClass(LogReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        job2.addCacheFile(cache_file.toURI());
        FileInputFormat.addInputPath(job2, new Path(input[1]));
        FileOutputFormat.setOutputPath(job2, new Path(input[3]));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);
    }

}


