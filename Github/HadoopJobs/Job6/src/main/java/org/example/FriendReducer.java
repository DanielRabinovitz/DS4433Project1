package org.example;

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.io.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//This file creates lists of the unique pages accessed and the friend list, then checks to see if
//any of the seen friends have never had their page visited by the user
public class FriendReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> vals, Context context) throws IOException, InterruptedException {

        //store every page in the accesslog that's been seen for the given key
        List<Text> seen_pages = new ArrayList<Text>();
        //store every page in the friends list that's been seen for the given key
        List<Text> seen_friends = new ArrayList<>();

        //Make lists of the all the unique values for each csv
        for(Text val : vals){
            //If the value came from the accesslog
            if(val.toString().contains("A")){
                //if the page hasn't been seen yet, add to list
                if(!seen_pages.contains(val)){seen_pages.add(val);}
            }
            //If the value came from friends
            else if (val.toString().contains("F")) {
                //If the friend hasn't been seen yet, add to list
                if(!seen_friends.contains(val)){seen_friends.add(val);}
            }
        }

        //Check to see if any items in seen_friends are not in seen_pages
        //if there are any mismatches, mark hasfakefriend to true
        boolean hasfakefriend = false;
        int friends_index = 0;
        //using a while loop to avoid a break statement
        //loop until the list is empty or a fake friend is found
        while(friends_index < seen_friends.size() && !hasfakefriend){

            //get the item
            Text val = seen_friends.get(friends_index);
            //convert the val to the same format as the other list (e.g. 1F => 1D)
            Text converted = new Text(val.toString().replace('F','D'));

            //if seen_pages doesn't contain the current friend's page ID, this is a fake friend
            if(!seen_pages.contains(converted)){hasfakefriend=true;}

            friends_index++;
        }

        //if they have a fake friend, write the user's ID to file
        if(hasfakefriend) {context.write(key, new Text("1"));}

    }
}

