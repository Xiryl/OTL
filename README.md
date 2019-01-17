# OTL

## Over The Light - Private Home Automation System

[![YDAFD](https://forthebadge.com/images/badges/you-didnt-ask-for-this.svg)](#)
[![Made With Love](https://forthebadge.com/images/badges/built-with-love.svg)](#)

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

Over The Light is my personal project for home automation system that allows you to communicate with the devices (sonoff) in the home safely and automatically. The system automatically detects connected devices at home and adds them to the server to allow them to be controlled from the Android app or Wear OS app.

## Guide
- [Install](#installation)
- [Authentication system with JWT](#how-authentication-and-device-command-work)
- [Project Structure](#project-structure)
- [Related](#related)

## Installation
To run the entire project you need:
- An MQTT broker server active
- MQTT libraries on pc
- Install all node dependencies with `npm install` for each component
- Fill the `config.json` file

```
{
    "MQTT": {
        "MQTT_BROKER_ADDRESS" : "mqtt://127.0.01, // MQTT Broker Server Address
        "MQTT_ALLOWED_DEVICES" : [
            "sonoff-1"                            // List of allowed IoT devices that can be access from MQTT controller
        ],
        "MQTT_ALLOWED_TOPICS" : [
            "bedroom"                             // List of allowed topics that can be access from MQTT controller
        ],      
        "MQTT_ALLOWED_COMMANDS" : [
            "ON", "OFF"                           // List of allowed commands that the user can send
        ]
    },
    "server" : {
        "SERVER_HOST" : "localhost",              // Where start express server
        "SERVER_PORT" : 5011                      // Express server port
    },
    "jwt" : {
        "JWT_ALLOWED_USERS" : [ "op6-fabio"],     // Users that can be access to Express server (API)
        "JWT_TOKEN_EXPIRATION" : "1h",            // JWT Experiation time
        "JWT_PRIVATE_KEY" : "fabiofabio"          // JWT Secret Key for sign
    }
}
```

## How Authentication and device command work

The authentication system use JWT + PrivateKey + User IP to authenticate the request. You can open the image in large format [here](https://github.com/Xiryl/OTL/blob/master/ot/IOT_Comunication.png?raw=true)

<img src="https://github.com/Xiryl/OTL/blob/master/ot/IOT_Comunication.png" />

## Project Structure
Each folder of the repo corresponds to an component. You can find these components:

- `droid`
  - Android app
  - Wear OS appIOT_Comunication.png
- `controller`
  - all the components that manage the back-end & MQTT broker
- `raspi`
  - all the components residing on raspi 3
  
#### Related
- [tasmota](https://github.com/arendst/Sonoff-Tasmota)
- [hass.io](https://github.com/home-assistant/hassio)
- [mqtt](https://www.hivemq.com)
