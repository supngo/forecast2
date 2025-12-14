#!/bin/bash

sleep 90
#chromium --kiosk --password-store=basic http://localhost:8080/forecast
chromium --start-fullscreen --password-store=basic http://localhost:8080/forecast
