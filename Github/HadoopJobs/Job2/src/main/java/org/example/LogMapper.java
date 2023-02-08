package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//reads in ids and counts them
public class LogMapper extends Mapper<Object, Text, Text, Text>{

    //stores the current CSV row
    private Text record = new Text();

    //map method
    public void map(Object key, Text val, Mapper.Context context)
            throws IOException, InterruptedException {
        //split the csv rows into an array, each array index being a col in the CSV
        //index #4 is the ID of the page visited
        String[] items = val.toString().split(",");
        //set record to what we want to write out
        record.set(items[4]);
        //give all records the same key because I want them all in one reducer
        context.write(record, new Text("1"));
    }

}
