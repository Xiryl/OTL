# OTL

## Over The Light - Private Home Automation System

[![YDAFD](https://forthebadge.com/images/badges/you-didnt-ask-for-this.svg)](#)
[![Made With Love](https://forthebadge.com/images/badges/built-with-love.svg)](#)

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

Over The Light is my personal project for home automation system that allows you to communicate with the devices (sonoff) in the home safely and automatically. The system automatically detects connected devices at home and adds them to the server to allow them to be controlled from the Android app or Wear OS app.

#### walkthrough
Each folder of the repo corresponds to an component. You can find these components:

- `droid`
  - Android app
  - Wear OS app
- `controller`
  - all the components that manage the back-end & MQTT broker
- `raspi`
  - all the components residing on raspi 3
  
#### related
- [tasmota](https://github.com/arendst/Sonoff-Tasmota)
- [hass.io](https://github.com/home-assistant/hassio)
- [mqtt](https://www.hivemq.com)
