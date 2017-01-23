# <p style='color:green'>BeBaMa Services</p>

Welcome to the BeBaMa webshop analytics microservice!
Our service provides useful info on your webshop, with advanced filtering possibilities.

## <p style='color:green'>Steps to use our service</p>
### <p style='color:green'>Integration</p>
* clone the repo
* set up the db connection in your IDE
* run ```/src/main/resources/public/sql/init_db.sql``` to create the db structure
* run ```AnalyticsService``` - it creates the necessary server configurations
* open ```localhost:60000``` and register your webshop
* follow the instructions on the screen: you'll have to copy some lines to the html of the index page of your webshop

### <p style='color:green'>Usage</p>
* with the selected route and parameters, open ```localhost:60000``` to get specific data
* or click on your webshop on the main page to get a summary of analytics
* please note, that you'll get JSON data
* all routes require your apiKey in the following format: **apiKey=xxxxxxxxxx**
* when applies, time format to use: **yyyyMMddHHmmss**

### <p style='color:green'>Enjoy!</p>

 ****

## <p style='color:green'>Webshop Analytics - beta version 2.0</p>
* ```/api``` route returns a summary of all data
  * parameters to search for a specific interval:
    * start
    * end
  * example query without interval: ```/api?apiKey=xxxxxxxxxx```
  * example response: ``` {"visitors":1,"times":{"average_time_spent":"00:03:45","min_time_spent":"00:03:45","max_time_spent":"00:03:45"},"webshop":"yourWebshop","most_visited_from":"Budapest, Magyarország, HU"}```
* ```/api/visitors``` route returns the total number of visitors
  * parameters to search for a specific interval:
    * start
    * end
  * example query with specific interval: ```/api/visitors?apiKey=xxxxxxxxxx&start=20170101000000&end=20170131000000```
  * example response: ```{"visitors":1,"from":Sun Jan 01 00:00:00 CET 2017,"webshop":"yourWebshop","to":Tue Jan 31 00:00:00 CET 2017}```
* ```/api/times``` route returns the average, minimum and maximum visit time a visitor spends in your webshop
  * parameters to search for a specific interval:
      * startTime
      * endTime
  * example query: ```/api/times?apiKey=xxxxxxxxxx```
  * example response: ```{"times":{"average_time_spent":"00:03:45","min_time_spent":"00:03:45","max_time_spent":"00:03:45"},"webshop":"yourWebshop"}```
* ```/api/locations``` returns the top location from which the webshop was visited (+how many times)
  * parameters to search for a specific interval:
    * startTime
    * endTime
  * example query: ```/api/locations?apiKey=xxxxxxxxxx```
  * example response: ```{"locations":{"Budapest, Magyarország, HU":1},"webshop":"yourWebshop"}```

## <p style='color:green'>Errors</p>
* The most common problem arises from a missing or wrong format apiKey. Please pay attention to spelling (```apiKey```), and copy all characters of your key.
  * In case of a missing apiKey, or a misspelled ```apiKey=``` in the request, you'll see the following error message: ```{"error":"ApiKey is required"}```
  * ```{"error":"Invalid apiKey"}``` will be displayed, if your apiKey is missing some charcters, or in an invalid format
* You either request data with only the apiKey, or with interval filters. in the latter case, please provide a ```start``` and an ```end``` time. If you miss any of those, you'll see ```{"error":"Invalid number of parameters"}```
* If you provide the right number of parameters, but misspelled either of them, you'll get ```{"error":"Invalid parameters"}```

BeBaMa 2017 [@makaimark](https://github.com/makaimark) [@balazsando](https://github.com/balazsando) [@cickib](https://github.com/cickib)
