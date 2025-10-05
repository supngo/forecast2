#!/bin/bash

sleep 20

echo -n "Starting Forecast application on Pi 3..."

/home/pi/.sdkman/candidates/java/current/bin/java -jar /home/pi/Forecast/target/forecast-1.0.jar > /home/pi/Forecast/forecast.log 2>&1 &

echo -n "Done!"
