#!/bin/bash

#disclaimer: this script was only tested with raspberry pi, for more OS, please check uhubctl website

today=`date '+%m-%d-%Y %H:%M:%S'`
#echo [$today]

day=$(date +"%u")  # 1=Mon, 7=Sun
hour=$(date +"%H")

if [ "$hour" -ge 22 ] || [ "$hour" -lt 7 ]; then
    echo "Turn on led light"
    sudo /home/pi/uhubctl/uhubctl -a on -p 2 -l 1-1
else
    echo "Turn off led light"
    sudo /home/pi/uhubctl/uhubctl -a off -p 2 -l 1-1
fi
