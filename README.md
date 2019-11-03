# TutorSearcher
An Andoid app to search for tutors at USC


# Set up the environment

## Database

If you do not have MySQL installed, please install MySQL server from [this website](https://dev.mysql.com/downloads/mysql/).

## set up your IDE

1. clone the repo
2. open eclipse
3. file > import > maven > existing maven project and select `server/TutorSearcher` and import it

## install maven 

https://maven.apache.org/download.cgi

# to run

in the `server/TutorSearcher` directory run

``mvn clean``

``mvn spring-boot:run`` 

right now you should see "application running" and then it'll keep running until you kill it

you can also run it by right clicking `TutorSearcherApplication.java` > run as > java application inside of eclipse 
