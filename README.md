# OTL

## Over The Light - Private Home Automation System

[![YDAFD](https://forthebadge.com/images/badges/you-didnt-ask-for-this.svg)](#)
[![Made With Love](https://forthebadge.com/images/badges/built-with-love.svg)](#)

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

Over The Light is my personal project for home automation system that allows you to communicate with smart devices (sonoff) at home safely. The system automatically detects connected devices at home and adds them to the server to allow them to be controlled from the Android app or Wear OS app.

> Attention: this project is under construction. The android app, or the server may not be work as expected. Need help or questions? [contact me :)](https://www.t.me/Xiryl) 

## Guide
- [Dafaq am I reinventing the wheel!?](#why-reinventing-the-wheel)
- [Install](#installation)
- [Android](#android)
- [Auth & API call workflow](#auth-and-api-call-workflow)
- [System Workflow - connection](#system-workflow-connection)
- [System Workflow - discovery](#system-workflow-discovery)
- [Project Structure](#project-structure)
- [Related](#related)
- [PR](#can-i-contribute)

## Why Reinventing the Wheel?
<img src="https://github.com/Xiryl/OTL/blob/master/ot/giphy.gif" />

**dafaq is this repo?** _why am I reinventing the wheel?_

Who has never wanted to try some smart IoT devices in their home? I am among these.
**The problem:** the official app for this stuff, eWeLink, has several security problems. 
- security concerns (clear HTTP traffic to China)
- terrible UI/UX
- lack of features

**My solution:** build my own home automation system. 

> _Smarter. More secure. **Sexier.**_

OTL offers:
- jwt + simmetric key + IP whitelist and authentication checks
- supports all devices on mqtt
- group IoT devices _by topic_
- single or grouped device control
- timers and actions
- sexy UI
- available on telegram, android, web and Postman (if you love raw requests 😏)
- full customization (block specific commands, APIs, devices, topics)
- compatible with Google Assistant and Alexa
- logs

## Installation
To run the entire project you need:
- An instance of MQTT broker server
- For shell testing, MQTT phao/mosquitto
- Modify the `config.json` file accordingly

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
        ],
        "MQTT_ALLOWED_ACTION_FOR_COMMAND" : "control"       // MQTT allowed actions command
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

Then `npm install` and `node server.js`

## Android
Open the project with Android Studio.

The app UI should look like these (_It would be different, I like to change the UI several times_):

<img src="https://github.com/Xiryl/OTL/blob/master/ot/mokup.png" width="600px" />

Please forgive me for the Android mockup on and iPhone X(s).

## Auth and API call workflow

The authentication system uses jwt + key + client IP. You can open a larger version of the image [HERE](https://raw.githubusercontent.com/Xiryl/OTL/master/ot/api_diagram.png?raw=true)

<img src="https://github.com/Xiryl/OTL/blob/master/ot/api_diagram.png" />

## System Workflow Connection

When a new device req. connection call this workflow. You can open a larger version of the image [HERE](https://github.com/Xiryl/OTL/blob/master/ot/devices_diagram.png?raw=true)

<img src="https://github.com/Xiryl/OTL/blob/master/ot/devices_diagram.png" />

## System Workflow Discovery

When a new device req. mqtt discovery call this workflow. You can open a larger version of the image [HERE](https://raw.githubusercontent.com/Xiryl/OTL/master/ot/discovery_diagram.png?raw=true)

<img src="https://github.com/Xiryl/OTL/blob/master/ot/discovery_diagram.png" />

## Project Structure
Each folder of the repo represents a component

- `droid`
  - Android app
  - Wear OS appIOT_Comunication.png
- `controller`
  - all the components for back-end
    - `server.js` contains API handler
    - `controller.js` contains mqtt handler
  
#### Related
- [tasmota](https://github.com/arendst/Sonoff-Tasmota)
- [hass.io](https://github.com/home-assistant/hassio)
- [mqtt](https://www.hivemq.com)
- [jwt.io](http://jwt.io)
- [draw.io](http://draw.io)
- [sonoff basic](http://bfy.tw/MGJI)
- [ewelink problems](https://www.iot-tests.org/2018/06/sonoff-basic-wifi/)

#### Can I contribute?
Pull requests are allowed. Please read `CONTRIBUTE.md` first.
