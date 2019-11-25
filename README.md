# TutorSearcher
An Andoid app to search for tutors at USC


# Set up the environment

## Database

1. If you do not have MySQL installed, please install MySQL server from [this website](https://dev.mysql.com/downloads/mysql/). Take note of your username and password. 
2. Install [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) also if it is not installed already.
3. Click on the server under My Connections
4. Click the button under File and paste in the SQL script found under `TutorSearcher/server/TutorSearcher/src/main/resources/tutorsearcher.sql`
5. In the SQL script tab click the lightning bolt button 

## Server

You can either run the back-end server in an IDE or on command line. 

### in an IDE

The server is tested to be able to run in Eclipse Jee. 
1. Extract the project from zip file to desired directory.
2. In Eclipse, File > Import > Maven > Existing Maven Projects and select the `TutorSearcher/server/TutorSearcher` directory.
3. Navigate to `src/main/resources/application.properties` and replace the database credentials with your own.
4. Under the Project Explorer or Package Explorer, navigate to `src/main/java/tutor.searcher.TutorSearcher` package. 
5. Right click `TutorSearcherApplication.java` > Run As > Java Application. Later runs should only require pressing the green run button at the top.

### on the command line

1. Install [Maven](https://maven.apache.org/download.cgi) and make sure it is in the PATH if it isn't already. 
2. In an editor of choice, open `TutorSearcher/server/TutorSearcher/src/main/resources/application.properties` and modify the database credentials.
3. In the project directory, navigate to `TutorSearcher/server/TutorSearcher` in the terminal.
4. Run `mvn spring-boot:run`. 

## Android Application

1. Install Android Studio
2. Open Android Studio and choose "Open an Existing Android Studio Project"
3. Open `TutorSearcher/TutorSearcherAndroid` as your Android Studio directory
4. Add configuration for the app and choose a device for the emulator
5. Run app

# Testing

## Server & Database
1. In MySQL workbench, create a new schema named `tutorsearchertest`
2. On the command line, run `mvn spring-boot:run -Drun.arguments="--spring.datasource.url=jdbc:mysql://localhost:3306/tutorsearchertest?serverTimezone=America/Los_Angeles"`
3. In a new terminal, run `mvn test`

## Android Application

