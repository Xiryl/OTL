const express       = require('express');
const bodyParser    = require('body-parser');
let authenticator   = require('../authenticator');
let loginHandler    = require('./server-handlers/login-handler');
const config        = require('../config/config.json');
let controller      = require('../mqtt-controller/controller');
let log             = require('./../logger/logger');
let slack           = require('./../slack/slack');
const cmdValidation = require('./serverDataValidator');
let customError     = require('./../customError/customError');

/*
let APISendCommandToMQTTBroker = (req, res, topic, dev, cmd) => {
    const client_ip = req.headers['x-forwarded-for'] || req.connection.remoteAddress;

                
                log.info(`Command from IP: ${client_ip}-'${topic}/${dev}/${cmd}' sent.`);
                
               /* slack.webhook({
                    channel: config.slack.SLACK_CHANNEL,
                    username: "VPS",
                    icon_emoji: ":bulb:",
                    text: `Light turned ${cmd} from user with IP:${client_ip}.`
                  }, function(err, res) {
                    // nothing
                  });*/
/*
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
}*/

let APISendCommandStatusToMQTTBroker = (req, res, topic, dev) => {
    const client_ip = req.headers['x-forwarded-for'] || req.connection.remoteAddress;
    // check if topic is on white list
    if(config.MQTT.MQTT_ALLOWED_TOPICS.includes(topic)) {
        // check if devices is on white list
        if(config.MQTT.MQTT_ALLOWED_DEVICES.includes(dev)) {  
                controller.getDeviceStatus(topic, dev, (status) => {
                    return res.json({
                        allowed: true,
                        message: `Status of ${topic}/${dev} is ${status}.`,
                        status : status
                    });
                });
        }
        else{
            log.error(`User request status from IP: ${client_ip}-'${topic}/${dev}/${cmd}' send invalid device.`);
            return res.json({
                allowed: false,
                message: `Invalid device '${dev}'.`
            });
        }
    }
    else {
        log.error(`User request status from IP: ${client_ip}-'${topic}/${dev}/' send invalid topic.`);
        return res.json({
            allowed: false,
            message: `Invalid topic '${topic}'.`
        });
    }
}

let start = () => {
    // TODO: make more secure like https://expressjs.com/it/advanced/best-practice-security.html

    let app = express();

    app.use(bodyParser.urlencoded({
        extended: true
    }));

    app.use(bodyParser.json());

    /** AUTH */
    /**======================================================================== */
    app.post('/auth', async (request, response)  => {

        const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
        const username  = request.body.client_username;

        try {
            const token = await loginHandler(client_ip, username);

            log.info(`Authenticated user:${username} with IP:${client_ip} and token:${token}`);

            return response.json({
                success: true,
                message: 'Authentication successful.',
                token: token
            });
        }
        catch(ex) {
            if(ex instanceof customError.UserNotAllowedException)
            {
                log.error(`An error occurring during authentication for ${username} and IP:${client_ip}. Error: ${ex.message}`);
                return response.json({
                    success: false,
                    message: ex.message
                });
            }
            else{
                log.error(`An error occurring during authentication for ${username} and IP:${client_ip}. Error: ${ex.message}`);
                return response.json({
                    success: false,
                    message: 'Whoops! An error occurred'
                });
            }
        }
    });

    /** COMMAND */
    /**======================================================================== */
    app.get('/:action/:topic/:device/:command', async (request, response) => {
        const token     = request.headers['x-access-token']  || request.headers['authorization'];
        const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
        const action    = request.params.action;
        const topic     = request.params.topic;
        const device    = request.params.device;
        const command   = request.params.command;

        log.debug(`Receiving command: '${action}/${topic}/${device}/${command}' from IP: ${client_ip}`);

        try {
            const res_auth = await authenticator.chechToken(token, client_ip);

            if(res_auth) {
                log.info(`User authenticated with IP: ${client_ip}. Checking command validation...`);

                try {
                    const res_cmd_validation = await cmdValidation.commandParamValidation(action, topic, device, command);
                    
                    if(res_cmd_validation) {
                        // launch command
                        controller.controlDevice(topic, device, command);
                    }
                }
                catch(Exception) {
                    log.error(`error: ${Exception}`);
                }
            }
        }
        catch(ex) {
            if(ex instanceof InvalidTokenException) {
                log.error(`An error occurring during authentication for IP:${client_ip}. Error: ${ex.message}`);
                return response.json({
                    success: false,
                    message: ex.message
                });
            }
            else if(ex instanceof UserIpChangesException) {
                log.error(`An error occurring during authentication for IP:${client_ip}. Error: ${ex.message}`);
                return response.json({
                    success: false,
                    message: ex.message
                });
            }
            else if(ex instanceof MissingTokenException) {
                log.error(`An error occurring during authentication for IP:${client_ip}. Error: ${ex.message}`);
                return response.json({
                    success: false,
                    message: ex.message
                });
            }
            log.error(`An error occurring during authentication for ${username} and IP:${client_ip}. Error: ${ex.message}`);
                return response.json({
                    success: false,
                    message: 'Whoops! An error occurred'
            });
        }

    });

     /** API call handler */ 
     app.get('/:action/:topic/:device', (request, response) => {
        const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
        const action     = request.params.action;
        const topic     = request.params.topic;
        const device    = request.params.device;

        log.debug(`Receiving status request: '${action}/${topic}/${device} from IP: ${client_ip}`);

        authenticator.chechToken(request, response, ( data ) => {
            if(!data) {
                return response.json({
                    allowed: true,
                    message: 'Something goes wrong. Retry.'
                });
            }
            else{
                log.info(`User authenticated with IP: ${client_ip}. Checking command validation...`);

                if(action === 'getstatus') {
                    APISendCommandStatusToMQTTBroker(request, response, topic, device); 
                }
            }
        });
    });
    
    app.listen(config.server.SERVER_PORT, () => {log.info(`Server is listening on port: ${config.server.SERVER_PORT}`);});
};

start();