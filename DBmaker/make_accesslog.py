import random_vals.random_vals as rv
import pandas
import random

#TODO make AccessLog.csv

access_df = pandas.DataFrame(columns=['AccessID', 'ByWho', 'WhatPage', 'TypeOfAccess', 'AccessTime'])

#range for random is 200,000 because that's the ID range
def make_idpair():
    #generate 2 random IDs
    friend1 = random.randrange(1,200001)
    friend2 = random.randrange(1,200001)
    #return friends as tuple to be split
    return (friend1,friend2)

id_list = [num for num in range(1,10000001)]
#generate friend reqs as tuples
id_pairs = [make_idpair() for num in range(1,10000001)]
#split tuples
visitors = [tuple[0] for tuple in id_pairs]
visiteds = [tuple[1] for tuple in id_pairs]
access_msgs = [rv.make_accessmsg() for num in range(1,10000001)]
access_times = [random.randrange(1,1000001) for num in range(1,10000001)]

access_df['AccessID'] = id_list
access_df['ByWho'] = visitors
access_df['WhatPage'] = visiteds
access_df['TypeOfAccess'] = access_msgs
access_df['AccessTime'] = access_times

#the csv is so big that it needs split into 8 files
for i in range(1,9):
    #num rows in csv
    csv_size = 1250000
    #set record range for the sub-dataframe
    baby_df = access_df[((i-1)*csv_size):((i*csv_size)+1)]
    #export to subfolder, customize for your system
    baby_df.to_csv(f"C:/Users/owner/Documents/Classes/DS4433Project1/DBmaker/AccessLog/AccessLog{i}.csv")

print('done')
