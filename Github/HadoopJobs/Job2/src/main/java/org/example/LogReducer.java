package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


import java.io.IOException;
import java.util.PriorityQueue;

//outputs the top 8 IDs by viewcount
public class LogReducer extends Reducer<Text, Text, Text, Text> {

    //A wrapper for KV pairs so that we can store them
    public class KVWrapper implements Comparable<KVWrapper>{

        public String pageID;
        public int viewCount;

        public KVWrapper(String pageID, int accesses){
            this.pageID=pageID;
            this.viewCount=accesses;
        }

        public int compareTo(KVWrapper kv){return Integer.compare(this.viewCount, kv.viewCount);}

    }

    private PriorityQueue<KVWrapper> top8 = new PriorityQueue<>();
    @Override
    public void reduce(Text key, Iterable<Text> vals, Context context) throws IOException, InterruptedException {

        //count how many times the id shows up
        int count = 0;
        for(Text val : vals){
            count++;
        }

        //add kv pair to priority queue
        top8.add(new KVWrapper(key.toString(),count));

        //if there's more than 8 items, get rid of the smallest one
        if(top8.size()>8){top8.poll();}

    }

    @Override
    protected void cleanup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        for(KVWrapper kv : top8){
            context.write(new Text(kv.pageID), new Text(Integer.toString(kv.viewCount)));
        }
        int stop=0;
    }
}
