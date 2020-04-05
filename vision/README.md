# 2020-pi-vision
Vision Code for rPi4B 2020 season

rPi Username/Password:
pi/6238popcorn

`ssh-keygen -t rsa` to create an ssh key (on local computer) if one doesn't already exist, `ssh-copy-id pi@frcvision.local` to copy your public key to the rPi

use `sudo timedatectl set-time "YYYY-MM-DD HH-mm-ss"` to set the time close to UTC time

use `sudo timedatectl set-timezone America/Los_Angeles` to set time zone

`sudo timedatectl set-ntp true` to sync with time servers, run `sudo reboot` after

`export PYTHONPATH="/usr/local/frc/lib/python3.7/dist-packages"` to allow usage of frc's libraries (specifically opencv)
