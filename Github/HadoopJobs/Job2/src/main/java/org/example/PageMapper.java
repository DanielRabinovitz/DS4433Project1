package org.example;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class PageMapper extends Mapper<Object, Text, Text, Text> {

    //this stores the KV pairs for the cached file
    private HashMap<String,String> accessCount = new HashMap<>();

    //need to make variables outside of map to write to context
    private Text outKey = new Text();
    private Text outVal = new Text();

    @Override
    protected void setup(Mapper<Object, Text, Text, Text>.Context context) throws IOException, InterruptedException {

        URI[] cacheFiles = context.getCacheFiles();
        Path path = new Path(cacheFiles[0]);

        // open the stream
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream fis = fs.open(path);

        // wrap it into a BufferedReader object which is easy to read a record
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,
                StandardCharsets.UTF_8));
        // read the record line by line
        String line;
        while (StringUtils.isNotEmpty(line = reader.readLine())) {
            System.out.println(line);
            String[] split = line.split("\t");
            accessCount.put(split[0], split[1]);
        }
        // close the stream
        IOUtils.closeStream(reader);
    }

    public void map(Object key, Text val, Mapper.Context context) throws IOException, InterruptedException {

            //items[1] = pageID
            //items[2] + items[3] = name and nationality
            String[] items = val.toString().split(",");
            System.out.println(items[1]);

            if(accessCount.get(items[1]) != null){
                //ID in my Page is index 1
                outKey = new Text(items[1]);
                String name_and_natl = items[2] + "," + items[3] + "," + accessCount.get(items[1]).toString();
                outVal = new Text(name_and_natl);
                //give all records the same key because I want them all in one reducer
                context.write(outKey, outVal);
            }

        }

}
