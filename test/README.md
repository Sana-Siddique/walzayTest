This project contains integrate of GiftLov apis using Spring boot.

To run this project, we need to setup redis cache locally.Redis configured should be running on port 6379

# **Details about endpoints**

First we generate token by hitting generateToken Api of GiftLuv, the endpoint is given below. 

_curl --location 'http://localhost:3021/login/generateToken' \
--header 'Content-Type: application/json' \
--data '{
"username":"coding_challenge_1",
"password":"coding_challenge_1"
}'_

the received token will then be used and store locally in redis so we don't need to hit
again and again for every api. We will retrieve token according to username from redis.
To get all orders, use the given below curl command

curl --location 'http://localhost:3021/order?current=1&rowCount=100'
