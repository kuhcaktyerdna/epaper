#!/bin/sh

echo "Waiting 10s for jdbc:postgresql://postgres:5432/${DB_NAME} to start"


sleep 10 && java -jar /epaper-0.0.1.jar
