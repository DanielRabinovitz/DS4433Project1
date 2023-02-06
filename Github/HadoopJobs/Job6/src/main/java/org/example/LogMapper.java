package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//This class gets the list of accessed pages for each friend
public class LogMapper extends Mapper<Object, Text, Text, Text> {

    public void map(Object key, Text val, Context context) throws IOException, InterruptedException {
        String[] items = val.toString().split(",");
        Text viewer = new Text(items[3]);
        Text pageViewed = new Text(items[4]);
        //recipient is the key because we're counting how many request people got
        context.write(viewer, pageViewed);
    }

}
