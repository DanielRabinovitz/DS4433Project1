import pandas
import os

#get the path to the DBmaker folder
db_path = os.path.dirname(os.path.abspath(__file__))
#variables for the path of the folders to be combined
friends_path = db_path + '\Friends'
accesslog_path = db_path + '\AccessLog'

#make empty df
df = pandas.DataFrame()

#add every csv in /Friends to the df
for file in os.listdir(friends_path):
    baby_df = pandas.read_csv(file)
    df.append(baby_df)

#load the df into a csv
#specify your filepath for the friends csv
df.to_csv('C:\Users\owner\Documents\Classes\DS4433Project1\CSVs\Friends.csv')

#make empty df
df = pandas.DataFrame()

#add every csv in /Friends to the df
for file in os.listdir(accesslog_path):
    baby_df = pandas.read_csv(file)
    df.append(baby_df)

#load the df into a csv
#specify your filepath for the friends csv
df.to_csv('C:\Users\owner\Documents\Classes\DS4433Project1\CSVs\AccessLog.csv')