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
4. Save the file above and reboot. After reboot, the forecase.service will start automatically to boot strap the forecast service. Then the browser will open at http://localhost:8080/forecast because of forecast.desktop
