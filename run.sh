#!/bin/bash

case "$1" in
	book)
		echo –n "Starting Forecast application on Mac Book..."
		nohup /Users/whizmate/Work/workspace/Forecast/forecast.sh book > /Users/whizmate/Work/workspace/Forecast/forecast.log&
		;;
	odroid)
		echo –n "Starting Forecast application on Odroid..."
		nohup /home/odroid/Work/Forecast/forecast.sh odroid > /home/odroid/Work/Forecast/forecast.log&
		;;
	nuc)
		echo –n "Starting Forecast application on NUC..."
		nohup /home/pony/Work/git/forecast/forecast.sh nuc > /home/pony/Work/git/forecast/forecast.log&
		;;
	pi)
    echo –n "Starting Forecast application on Pi 3..."
    nohup /home/pi/Forecast/forecast.sh pi > /home/pi/Forecast/forecast.log&
		;;
	stop) 
		osType=$(uname -s)
		case "$osType" in
			"Darwin")
			{
				echo "Stopping Forecast application on Mac OSX..."
				kill -9 $(lsof -ti tcp:8080)
			};;
			"Linux")
			{
				echo "Stopping Forecast application on Linux..."
				fuser -k 8080/tcp
			};;
		esac
		;;
	*)
	echo "Usage: ./run.sh {book|odroid|nuc|pi|stop}"
		exit 1
        ;;
esac
