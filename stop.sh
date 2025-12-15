#!/bin/bash
# Script to stop any process using port 8080

# Find the PID of the process using port 8080
PID=$(sudo lsof -t -i:8080)

if [ -n "$PID" ]; then
  echo "Process using port 8080 found: PID=$PID"
  echo "Stopping Forecast application..."
  sudo kill -9 $PID
  echo "Process stopped."
else
  echo "No process is using port 8080."
fi
