# Getting Started

### Run Forecast in start up (Raspberry Pi)
1. Create a service at /etc/systemd/system (ex: forecast.service)
   assume we store the code at /home/pi/Forecast
```
[Unit]
Description=Forecast Java Service
After=network-online.target
Wants=network-online.target


[Service]
ExecStart=/home/pi/Forecast/run.sh
WorkingDirectory=/home/pi/Forecast
StandardOutput=append:/home/pi/Forecast/forecast.log
StandardError=append:/home/pi/Forecast/forecast.log
Restart=on-failure
User=pi

[Install]
WantedBy=multi-user.target
```
2. Save the file above and run the below commands:
```
sudo systemctl daemon-reload
sudo systemctl enable forecast.service
sudo systemctl start forecast.service
```
3. Create desktop service at /etc/xdg/autostart (ex:forecast.desktop)
```
[Desktop Entry]
Type=Application
Name=Chromium Browser (Delayed)
Exec=sh -c "sleep 60 && chromium-browser --kiosk http://localhost:8080/forecast"
X-GNOME-Autostart-enabled=true
```
4. Save the file above and reboot. After reboot, the forecast.service will start automatically to boot strap the forecast service. Then the browser will open at http://localhost:8080/forecast thanks to forecast.desktop

### Check GPS usb dongle (VK-162) (Raspberry Pi)
enter the below
```
ls /dev/ttyUSB*
```
Or 
```
ls /dev/ttyACM*
```
Then enter
```
cat dev/ttyACM*
```
you will see something like
```
$GPGGA,224741.00,3856.57699,N,07724.49250,W,...
```
That means:
- Latitude: 3856.57699,N → 38°56.57699' N
- Longitude: 07724.49250,W → 77°24.49250' W

We need to convert NMEA format (degrees + minutes) into decimal degrees.

