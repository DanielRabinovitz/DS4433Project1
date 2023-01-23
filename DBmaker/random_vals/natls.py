import random
import string
import json

## DO NOT RUN, this script was used to generate nationalities ##

#make a dict of random strings and country codes
nationalities = {}
for i in range(1,51):
    nationalities[i] = ''.join(random.choices(string.ascii_lowercase, k=random.randrange(10,20)))

#store as a json and write to file
with open ('C:/Users/owner/Documents/Classes/DS4433Project1/DBmaker/random_vals/nationalities.json', 'w', encoding='utf-8') as nationalities_file:
    json.dump(nationalities, nationalities_file, indent=3)
