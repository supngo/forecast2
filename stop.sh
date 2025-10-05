#!/bin/bash

echo "Stopping Forecast application..."
fuser -k 8080/tcp
