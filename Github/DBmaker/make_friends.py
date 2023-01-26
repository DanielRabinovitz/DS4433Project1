import random_vals.random_vals as rv
import pandas
import random

#TODO make Friends.csv

#make empty dataframe
friends_df = pandas.DataFrame(columns= ['FriendRe1', 'PersonID', 'MyFriend', 'DateOfFriendship', 'Desc'])

#range for random is 200,000 because that's the ID range
def make_friendreq():
    #generate 2 random IDs
    friend1 = random.randrange(1,200001)
    friend2 = random.randrange(1,200001)
    #check if they are the same
    #if yes, find a new friend2
    if friend1==friend2:
        friend2 = random.randrange(1,200001)
    #return friends as tuple to be split
    return (friend1,friend2)

#generate lists
id_list = [num for num in range(1,20000001)]
#generate friend reqs as tuples
friend_reqs = [make_friendreq() for num in range(1,20000001)]
#split tuples
friend1_list = [tuple[0] for tuple in friend_reqs]
friend2_list = [tuple[1] for tuple in friend_reqs]
dates = [random.randrange(1,1000001) for num in range(1,20000001)]
descriptions = [rv.make_relationship() for num in range(1,20000001)]

#set cols to equal generated lists
friends_df['FriendRe1'] = id_list
friends_df['PersonID'] = friend1_list
friends_df['MyFriend'] = friend2_list
friends_df['DateOfFriendship'] = dates
friends_df['Desc'] = descriptions

#the csv is so big that it needs split into 8 files
for i in range(1,17):
    #num rows in csv
    csv_size = 1250000
    #set record range for the sub-dataframe
    baby_df = friends_df[((i-1)*csv_size):((i*csv_size)+1)]
    #export to subfolder, customize for your system
    baby_df.to_csv(f"C:/Users/owner/Documents/Classes/DS4433Project1/DBmaker/Friends/Friends{i}.csv")

print('done')
