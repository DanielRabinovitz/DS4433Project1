package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
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
        input[2] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1Outputs\\job6output1";
        //and job2 here
        input[3] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1Outputs\\job6output2";
        //program throws an error if the output folder exists, so delete the folder
        FileUtils.deleteDirectory(new File(input[2]));
        FileUtils.deleteDirectory(new File(input[3]));

        Configuration conf = new Configuration();
        Configuration conf2 = new Configuration();
        Configuration conf3 = new Configuration();
        Job job = Job.getInstance(conf, "Get friends");
        Job job2 = Job.getInstance(conf2, "Get logs");
        Job job3 = Job.getInstance(conf3, "Join and delete");

        //run the job to find the list of friends
        job.setJarByClass(Job6.class);
        job.setMapperClass(FriendListMapper.class);
        job2.setReducerClass(FriendReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(input[0]));
        FileOutputFormat.setOutputPath(job, new Path(input[2]));
        job.waitForCompletion(true);

        //run the job to find the list of accessed pages
        job2.setJarByClass(Job6.class);
        job2.setMapperClass(LogMapper.class);
        job2.setReducerClass(LogReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job2, new Path(input[1]));
        FileOutputFormat.setOutputPath(job2, new Path(input[3]));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);

        //how do I pass these both in to one reducer
        job3.setJarByClass(Job6.class);
        //If possible I want a mapside join
        //Have the k,v pair look like
        //  (Text ID, [List<Text> FriendList, List<Text>Access List])
        job3.setMapperClass(JoinMapper.class);
        //and then use the reducer to subtract stuff
        job3.setReducerClass(SubtractionReducer.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job3, new Path(input[2],input[3]));
        FileOutputFormat.setOutputPath(job3, new Path(input[3]));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);

    }
}