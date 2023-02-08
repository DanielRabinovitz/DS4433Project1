package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//This class gets the friend list for each sender
public class FriendListMapper extends Mapper<Object, Text, Text, Text> {

    public void map(Object key, Text val, Context context) throws IOException, InterruptedException {

        String[] items = val.toString().split(",");
        Text sender = new Text(items[3]);
        Text recipient = new Text();

        //We have two output files with the same keys
        //If the key/val pair comes from the AccessLog, append an A
        //If the key/val pair comes from the friends list, append an F
        if(context.getInputSplit().toString().contains("AccessLog.csv")){
            recipient = new Text(items[4] + "A");
        } else if (context.getInputSplit().toString().contains("Friends.csv")) {
            recipient = new Text(items[4] + "F");
        }

        //recipient is the key because we're counting how many request people got
        context.write(sender, recipient);
    }

}
