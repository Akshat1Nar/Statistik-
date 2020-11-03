export CLASSPATH=$CLASSPATH:./mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar
export CLASSPATH=$CLASSPATH:./Unfolding_Maps/lib/*
export CLASSPATH=$CLASSPATH:./Processing/*

javac setup/*.java gui/*.java

java gui/Interface
