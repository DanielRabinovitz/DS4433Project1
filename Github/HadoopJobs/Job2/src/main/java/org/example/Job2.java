package org.example;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//This file is meant to do the following hadoop job:
//Find the 8 pages with the most visits, the return page ID, Name, and Nationality for each of the top 8 pages
public class Job2{

    //First part of the mapreduce on Friends to get the top 8
    public static class LogMapper
    extends Mapper<Object, Text, Text, IntWritable>{

        //stores the current CSV row
        private Text record = new Text();

        //map method
        public void map(Object key, Text val, Context context)
        throws IOException, InterruptedException {

            //split the csv rows into an array, each array index being a col in the CSV
            //index #4 is the ID of the page visited
            String[] items = val.toString().split(",");
            //set record to what we want to write out
            record.set(items[4]);
            //System.out.println(record.toString());
            //write the k/v pair to hadoop
            //context is the thing that interacts with hadoop's magic backend
            //context only takes objects that are writeable and do comparison things I don't understand
            //that means I need Text and IntWritable over String and int
            context.write(record, new IntWritable(1));
        }
    }

    //Class for creating the list of the top 8 pages
    public static class LogReducer
    extends Reducer<Text, IntWritable, Text, IntWritable>{

        //make a tree map (hashmap sorted by
        private HashMap<Text, IntWritable> viewCounts = new HashMap<>();

        //fn to increment by 1 for the view count of a page
        private void updateCounts(Text pageID){
            //.get() on an IntWriteable gets the int stored inside of it
            int count = viewCounts.get(pageID).get();
            //update the page count with a new IntWritable
            viewCounts.put(pageID, new IntWritable(count++));
        }

        //counts how many items there are under each key
        public void reduce(Text key, Iterable<IntWritable> vals, Context context) throws IOException, InterruptedException {

            //count goes here at the end
            IntWritable result = new IntWritable();
            Text new_key = new Text("ID #");
            new_key = new Text(new_key.toString().concat(key.toString()));

            //store the count, run a for loop for each key
            int count = 0;
            for (IntWritable val : vals) {
                count += val.get();
            }
            //put the count in hadoop readable form and send to hadoop
            result.set(count);
            context.write(new_key, result);
        }

    }

    //Job (map only) to get the records for each of the top 8 items

    public static void main(String[] args) throws Exception {

        String[] input = new String[2];
        input[0] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1CSVs\\AccessLog.csv";
        //delete output folder between tests
        input[1] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1Outputs\\job2output";

        Configuration conf = new Configuration();
        Job job2 = Job.getInstance(conf, "Find top 8 pages");
        //sets the things to run for map and reduce
        job2.setJarByClass(Job2.class);
        job2.setMapperClass(LogMapper.class);
        job2.setCombinerClass(LogReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job2, new Path(input[0]));
        FileOutputFormat.setOutputPath(job2, new Path(input[1]));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);
    }

}

