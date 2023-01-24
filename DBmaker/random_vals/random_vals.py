import json
import os
import random
import string

#get current directory and shave off the script name
current_dir = os.path.realpath(__file__)
current_dir = current_dir.replace('random_vals.py','')
print(current_dir)

#quick fn to load files in
#assumes that all files are in the same folder as 
#this script, and that files are in json format
def load_to_list(filename):
    #get filepath
    filepath = current_dir + '/' + filename
    #load file
    with open(filepath, "r") as file:
        content = file.read()
    #return list
    content = json.loads(content)
    return content


## MyPage ##

#Names
first_names = load_to_list('firstnames.json')
last_names = load_to_list('surnames.json')

#fn to make a random name
def make_name():
    return random.choice(first_names) + ' ' + random.choice(last_names)

#Nationality
#randomly generated strings stored in a json somewhere else
nationalities = load_to_list('nationalities.json')

#fn to get a random country and corresponding country code as a tuple
def get_rand_country():
    country_code = random.randrange(1,51)
    return (nationalities[f'{country_code}'], country_code)

#Hobbies
hobbies = ['Video Games', 'Playing Cards', 'Growing Plants', 'Cooking Food', 'Standup Comedy', 
'Camping & Hiking', 'Wine Tastings', 'Doing Homework', 'Vexilology', 'Data Science']

#fn to get a random hobby
def get_rand_hobby():
    return random.choice(hobbies)

## Friends ##

#the description field
#to be structured as 'relationship_type[x] through their relationship_cause[y]
relationship_type = ['friends', 'dating', 'married']
relationship_cause = ['workplace', 'university', 'religious group', 
'family members', 'hobbies', 'mutual friends']

#fn to make a relationship description
def make_relationship():
    return random.choice(relationship_type) + ' through their ' + random.choice(relationship_cause)

## AccessLog ##

#for TypeOfAccess
#to be structured as 'access_types[x] access_age[y] post_type[z] from the user'
access_types = ['liked a', 'commented on', 'disliked', 'reposted', 'embedded link for', 'viewed a']
access_age = ['a new', 'an old']
post_type = ['post', 'status', 'timeline update', 'story', 'artpiece', 'video', 'image']

#fn to make an accesslog message
def make_accessmsg():
    return random.choice(access_types) + ' ' + random.choice(access_age) + ' ' + random.choice(post_type) + ' from the user'





