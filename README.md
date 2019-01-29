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

```JSON
{
    "MQTT": {
        "MQTT_BROKER_ADDRESS" : "mqtt://127.0.16.1",        // MQTT broker address  
        "MQTT_ALLOWED_DEVICES" : [                          // MQTT allowed devices to control from intranet
            "sonoff-1"
        ],
        "MQTT_ALLOWED_TOPICS" : [                           // MQTT allowed topics to control from intranet
            "bedroom"
        ],
        "MQTT_ALLOWED_COMMANDS" : [                         // MQTT allowed commands to control from intranet
            "ON", "OFF", "getstatus"
        ]
    },
    "server" : {
        "SERVER_HOST" : "127.0.0.1",                        // Server IP host
        "SERVER_PORT" : 5000,                               // Server port
        "FILENAME_LOG" : "out-log.log",                     // Server log file
        "ALLOWED_ACTIONS" : [                               // Server allowed API actions
            "control",
            "getstatus"
        ]
    },
    "jwt" : {
        "JWT_ALLOWED_USERS" : [ "mbp-fabio"],               // JWT allowed users
        "JWT_TOKEN_EXPIRATION" : "1h",                      // JWT token expiration (h)
        "JWT_PRIVATE_KEY" : "maow"                          // JWT signature
    },
    "slack" : {
        "SLACK_WEBHOOK": "https://hooks.slack.com/...",     // Slack webhook
        "SLACK_CHANNEL": "#maow"                            // Slack channel
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
