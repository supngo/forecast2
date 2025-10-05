#!/bin/bash

sleep 20

echo -e "Starting Forecast application on Pi 3...\n"

/home/pi/.sdkman/candidates/java/current/bin/java -jar /home/pi/Forecast/target/forecast-1.0.jar >> /home/pi/Forecast/forecast.log 2>&1

