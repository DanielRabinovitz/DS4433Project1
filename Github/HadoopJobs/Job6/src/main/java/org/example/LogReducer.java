package org.example;

import org.apache.hadoop.mapreduce.Reducer;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogReducer extends Reducer<Text, Iterable<Text>, Text, Text> {

    public void reduce(Text key, Iterable<Text> vals, Context context) throws IOException, InterruptedException {

        //store every page that's been seen for the given key
        List<Text> seen_pages = new ArrayList<Text>();

        //for every page that hasn't been seen,
        for(Text val : vals){

            //if the page has been seen before do nothing
            if(seen_pages.contains(val)){}
            //otherwise, write to context and add to seen pages list
            else{
                context.write(key, val);
                seen_pages.add(val);
            }

        }

    }
}
