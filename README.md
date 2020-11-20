# Statistik-
- [Run the project](#run-the-project)
- [Project Structure](#project-structure)
- [Project](#project)


### Project Structure

  #### 1. Class Diagram

  ![](/resources/CLASS_DIAGRAM.png)


  #### 1. Entity Relationship Diagram

  ![](/resources/ER_DIAGRAM.png)


### Project

  This project takes dynamic data as an input (fetches from csv files)
  and updates it periodically into *mysql* database.

  And from maintained database it gives you live update on every city 
  and how much safe a person is from various disasters and crimes of a 
  particular region.

  This is a demo-project (it lacks updated data) and it is mainly aimed 
  for tourists.

  ![](/resources/SC.jpg)


### Run the Project

  1. In setup/Setup.java replace with following

  > static final String USER = "root"

  > static final String PASS = "your_root_sql_password"

    or *add user* with given specifiactions


  2. On Linux/Unix environments use given commands from within
  the project's directory.

  > chmod +x ./run.sh

  > ./run.sh
