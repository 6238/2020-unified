#!/bin/bash
apt update;
apt upgrade;
apt install vim;
apt install python3-pip;
apt install python3-opencv;
echo 'deb http://download.opensuse.org/repositories/home:/auscompgeek:/robotpy/xUbuntu_20.04/ /' | sudo tee /etc/apt/sources.list.d/home:auscompgeek:robotpy.list;
wget -nv https://download.opensuse.org/repositories/home:auscompgeek:robotpy/xUbuntu_20.04/Release.key -O "/etc/apt/trusted.gpg.d/home:auscompgeek:robotpy.asc";
apt update;
apt install python3-cscore;
echo "opencv and cscore have been successfully installed.";
wget https://gist.githubusercontent.com/stevejobs3768/8708e75d8d1be84dc2e9873383b7b1d9/raw/35d0a7a572c527f06d1d0689bffee9c73191a479/usbcvstream.py;
python3 usbcvstream.py;
xdg-open http://0.0.0.0:8081
