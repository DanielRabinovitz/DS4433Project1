package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.util.Map;

//Identify users who have at least one friend who's page they have never visited
//1) Get friend list for each sender
//2) Get list of all unique pages accessed by each sender
//3) Remove all elements in common between the two lists
//4) Display whatever is left in the edited friend list
public class Job6 {

    public static void main(String[] args) throws Exception {

        String[] input = new String[4];
        //get data from here for job
        input[0] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1CSVs\\Friends.csv";
        //then from here for job2
        input[1] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1CSVs\\AccessLog.csv";
        //output job here
        input[2] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1Outputs\\job6output";

        //program throws an error if the output folder exists, so delete the folder
        FileUtils.deleteDirectory(new File(input[2]));

        Configuration conf = new Configuration();


        Job job = Job.getInstance(conf, "Get friends and logs");
        job.setJarByClass(Job6.class);
        job.setMapperClass(FriendListMapper.class);
        job.setReducerClass(FriendReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        MultipleInputs.addInputPath(job, new Path(input[0]), TextInputFormat.class, FriendListMapper.class);
        MultipleInputs.addInputPath(job, new Path(input[1]), TextInputFormat.class, FriendListMapper.class);
        FileOutputFormat.setOutputPath(job, new Path(input[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}