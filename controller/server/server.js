const express       = require('express');
const bodyParser    = require('body-parser');
let authenticator   = require('../authenticator');
let loginHandler    = require('./server-handlers/login-handler');
const config        = require('../config/config.json');
const controller    = require('../mqtt-controller/controller');
let log             = require('./../logger/logger');
let slack           = require('./../slack/slack');


let APISendCommandToMQTTBroker = (req, res, topic, dev, cmd) => {
    const client_ip = req.headers['x-forwarded-for'] || req.connection.remoteAddress;
    // check if topic is on white list
    if(config.MQTT.MQTT_ALLOWED_TOPICS.includes(topic)) {
        // check if devices is on white list
        if(config.MQTT.MQTT_ALLOWED_DEVICES.includes(dev)) {  
            // check if command is on white list
            if(config.MQTT.MQTT_ALLOWED_COMMANDS.includes(cmd)) {
                controller(topic, dev, cmd);
                log.info(`Command from IP: ${client_ip}-'${topic}/${dev}/${cmd}' sent.`);
                
                slack.webhook({
                    channel: config.slack.SLACK_CHANNEL,
                    username: "VPS",
                    icon_emoji: ":bulb:",
                    text: `Light turned ${cmd} from user with IP:${client_ip}.`
                  }, function(err, res) {
                    console.log(err);
                  });

                return res.json({
                    allowed: true,
                    message: `Command ${cmd} sended to ${topic}/${dev}/${cmd} successfully.`
                });
            }
            else{
                log.error(`User from IP: ${client_ip}-'${topic}/${dev}/${cmd}' send invalid command.`);
                return res.json({
                    allowed: false,
                    message: `Invalid command '${cmd}'.`
                });
            }
        }
        else{
            log.error(`User from IP: ${client_ip}-'${topic}/${dev}/${cmd}' send invalid device.`);
            return res.json({
                allowed: false,
                message: `Invalid device '${dev}'.`
            });
        }
    }
    else {
        log.error(`User from IP: ${client_ip}-'${topic}/${dev}/${cmd}' send invalid topic.`);
        return res.json({
            allowed: false,
            message: `Invalid topic '${topic}'.`
        });
    }
}

let start = () => {
    // Todo: make more secure like https://expressjs.com/it/advanced/best-practice-security.html

    let app = express();

    app.use(bodyParser.urlencoded({
        extended: true
    }));

    app.use(bodyParser.json());

    /** PAI auth handler */ 
    app.post('/auth', (request, response)  => {
        loginHandler(request, response);
    });

    /** API call handler */ 
    app.get('/:topic/:device/:command', (request, response) => {
        const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
        const topic     = request.params.topic;
        const device    = request.params.device;
        const command   = request.params.command;

        log.debug(`Receiving request: '${topic}/${device}/${command}' from IP: ${client_ip}`);

        authenticator.chechToken(request, response, ( data ) => {
            if(!data) {
                return response.json({
                    allowed: true,
                    message: 'Something goes wrong. Retry.'
                });
            }
            else{
                log.info(`User authenticated with IP: ${client_ip}. Checking command validation...`);
                APISendCommandToMQTTBroker(request, response, request.params.topic, request.params.device, request.params.command); 
            }
        });
    });
    
    app.listen(config.server.SERVER_PORT, () => {log.info(`Server is listening on port: ${config.server.SERVER_PORT}`);});
};

start();