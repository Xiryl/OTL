# OTL

## Over The Light - Private Home Automation System

[![YDAFD](https://forthebadge.com/images/badges/you-didnt-ask-for-this.svg)](#)
[![Made With Love](https://forthebadge.com/images/badges/built-with-love.svg)](#)

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

Over The Light is my personal project for home automation system that allows you to communicate with the devices (sonoff) in the home safely and automatically. The system automatically detects connected devices at home and adds them to the server to allow them to be controlled from the Android app or Wear OS app.

## Guide
- [Install](#installation)
- [Auth & API call workflow](#auth-&-api-call-workflow)
- [System Workflow - connection](#system-workflow-connection)
- [System Workflow - discovery](#system-workflow-discovery)
- [Project Structure](#project-structure)
- [Related](#related)
- [PR](#pr)

## Installation
To run the entire project you need:
- MQTT broker server
- MQTT phao/mosquitto client for testing via shell
- Fill the `config.json` file

Then run `npm install` on each component.


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

## Auth & API call workflow

The authentication system use jwt + key + client IP. You can open the image in large format [HERE](https://raw.githubusercontent.com/Xiryl/OTL/master/ot/api_diagram.png?raw=true)

<img src="https://github.com/Xiryl/OTL/blob/master/ot/api_diagram.png" />

## System Workflow Connection

When a new device req. connection call this workflow. You can open the image in large format [HERE](https://github.com/Xiryl/OTL/blob/master/ot/devices_diagram.png?raw=true)

<img src="https://github.com/Xiryl/OTL/blob/master/ot/devices_diagram.png" />

## System Workflow Discovery

When a new device req. mqtt discovery call this workflow. You can open the image in large format [HERE](https://raw.githubusercontent.com/Xiryl/OTL/master/ot/discovery_diagram.png?raw=true)

<img src="https://github.com/Xiryl/OTL/blob/master/ot/discovery_diagram.png" />

## Project Structure
Each folder of the repo corresponds to an component. You can find these components:

- `droid`
  - Android app
  - Wear OS appIOT_Comunication.png
- `controller`
  - all the components for back-end
  
#### Related
- [tasmota](https://github.com/arendst/Sonoff-Tasmota)
- [hass.io](https://github.com/home-assistant/hassio)
- [mqtt](https://www.hivemq.com)

#### PR
PR are allowed.
For make a PR plesase read the wiki/howtoPR
