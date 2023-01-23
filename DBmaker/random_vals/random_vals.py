import json
import os
import random
import string

#get current directory and shave off the script name
current_dir = os.path.realpath(__file__)
current_dir = current_dir.replace('random_vals.py','')
print(current_dir)

#quick fn to load files in
#assumes that all files are in the same folder as this script
def load_to_list(filename):
    #get filepath
    filepath = current_dir + '/' + filename
    #load file to list
    with open(filepath, "r") as file:
        content = file.read()
    #return list
    return content


## MyPage ##

#Names
first_names = load_to_list('firstnames.json')
last_names = load_to_list('surnames.json')

#fn to make a random name
def make_name():
    return random.choice(first_names) + random.choice(last_names)

#Nationality
#randomly generated strings stored in a json somewhere else
nationalities = load_to_list('nationalities.json')


#Hobbies
hobbies = ['Video Games', 'Playing Cards', 'Growing Plants', 'Cooking Food', 'Standup Comedy', 
'Camping & Hiking', 'Wine Tastings', 'Doing Homework', 'Vexilology', 'Data Science']

## Friends ##

#the description field
#to be structured as 'relationship_type[x] through their relationship_cause[y]
relationship_type = ['friends', 'dating', 'married']
relationship_cause = ['workplace', 'university', 'religious group', 
'family members', 'hobbies', 'mutual friends']

## AccessLog ##

#for TypeOfAccess
#to be structured as 'access_types[x] access_age[y] post_type[z] from the user'
access_types = ['liked', 'commented', 'disliked', 'reposted', 'embedded link for', 'viewed']
access_age = ['a new', 'an old']
post_type = ['post', 'status', 'timeline update', 'story', 'artpiece', 'video', 'image']







