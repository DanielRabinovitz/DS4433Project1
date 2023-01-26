import random_vals.random_vals as rv
import pandas
import random

#DO NOT RERUN, this makes a random database configuration

#make lists to turn into columns; ID, Name, Nationality, CountryCode, Hobby
id_list = [num for num in range(1, 200001)]
name_list = [rv.make_name() for ID in id_list]
#gets nationalities+countrycode as a list of tuples to be split up
natl_tuples = [rv.get_rand_country() for ID in id_list]
#split list of tuples into two lists
natl_list = [tup[0] for tup in natl_tuples]
ccode_list = [tup[1] for tup in natl_tuples]
hobby_list = [rv.get_rand_hobby() for ID in id_list]

mypage_df = pandas.DataFrame(columns=['ID', 'Name', 'Nationality', 'CountryCode', 'Hobby'])
mypage_df['ID'] = id_list
mypage_df['Name'] = name_list
mypage_df['Nationality'] = natl_list
mypage_df['CountryCode'] = ccode_list
mypage_df['Hobby'] = hobby_list

mypage_df.to_csv('MyPage.csv')

