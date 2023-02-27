
# Readme

This is a simple Rest Api created using Spring-Boot with in memory H2 database. The object Model is Candidate with fname(string), lname(string), email(string) and score(int).
When the Api started, it will preloaded with 4 Candidates with predefined names and random scores from 60 to 100.

Original java code is tested under Windows environment. Docker file is tested under both Windows and EC2 linux environment.

## Install via Git

1. Make sure JDK/JRE 17+ (https://www.oracle.com/au/java/technologies/downloads/) and maven (https://maven.apache.org/download.cgi) are installed in the local environment.
Use following command in command line to check.
```sh
java -version
mvn -version
```
2. Use git clone to copy repo to local
```sh
git clone https://github.com/ThomasWYang/candidate_spring.git
```
3. Change directory into that folder and run following command, the app will run on default port 8080.
```sh
./mvnw clean spring-boot:run
```
Or
```sh
mvn clean spring-boot:run
```
If you see this info is displayed, the app is running correctly
![图片](https://user-images.githubusercontent.com/84115795/219512379-42235cb8-0569-4b2c-a6e8-592e8f5369f5.png)

Please refer to https://stackoverflow.com/questions/34253779/tomcat-server-error-port-8080-already-in-use if there is error mentioning that port 8080 already in use.

## Test in local environment

Run the following command to execute Unit Test
```sh
./mvnw test
```
Or
```sh
mvn test
```
![图片](https://user-images.githubusercontent.com/84115795/219512711-6f713a0c-11a8-478e-a95d-a717434f60c9.png)

## Install via Docker

1. Make sure Docker desktop (https://www.docker.com/) is running.
2. Pull the docker image from docker hub.
```sh
docker pull thomasy2022/candidate_spring
```
3. Run the following command to run the app in a container. The app will run on port 8080 with below command.
```sh
docker run -d -p 8080:8080 thomasy2022/candidate_spring
```
4. You can also use docker-compose.yml file inside this repo combined with the docker image to run the app
```sh
docker compose up
```
It will run on configured port 8080 and you will see similar info below. 
![图片](https://user-images.githubusercontent.com/84115795/219513462-6ecf3fe1-9ce0-4bc9-aa5a-f368f246212d.png)

## Run the app directly from instance deployed in AWS EC2

Try http://13.236.184.156/candidates in browser.
Replace "localhost:8080" with "13.236.184.156" for all the command below if you wish to test the api deployed in AWS EC2.
```sh
curl -v "13.236.184.156/candidates/scoregreater/90"
```

## Usage

You may test the script via various tools like browsers and postman, I will show how to use curl to test the api in windows cmd or linux below. (add -v after curl to show detailed info)
1. Show all candidates
```sh
curl -v "localhost:8080/candidates"
```
2. Show single candidate by ID, if exist, that candidate will be returned. 
```sh
curl "localhost:8080/candidates/1"
```
3. Show single candidate by ID, if not exist, Not found info will be returned. 
```sh
curl "localhost:8080/candidates/99"
```
4. Search candidates by fname, lname or email (you can give any combination of these 3 fields).
```sh
curl “localhost:8080/candidates?fname=Thomas&lname=Yang”
```
```sh
curl “localhost:8080/candidates?email=thomas.y2022@gmail.com”
```
5. Search candidates by minimum score
```sh
curl “localhost:8080/candidates/scoregreater/80"
```
6. Create candidate with fname, lname and email (id will be generated automatically)
```sh
curl -v -X POST "localhost:8080/candidates" -H "Content-type:application/json" -d "{\"fname\": \"aaa\", \"lname\": \"bbb\", \"email\":\"aaa@gmail.com\"}"
```
7. Create candidate with fname, lname, email and score (id will be generated automatically)
```sh
curl -v -X POST "localhost:8080/candidates" -H "Content-type:application/json" -d "{\"fname\": \"mmm\", \"lname\": \"nnn\", \"email\":\"mmm@gmail.com\", \"score\":80}"
```
8. Update candidate with id
```sh
curl -v -X PUT "localhost:8080/candidates/4" -H "Content-type:application/json" -d "{\"fname\": \"xxx\", \"lname\": \"yyy\", \"email\":\"xxx@gmail.com\", \"score\":90}"
```
9. Delete candidate with id
```sh
curl -v -X DELETE "localhost:8080/candidates/4"
```
