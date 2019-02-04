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
let helmet          = require('helmet');

let start = () => {

    let app = express();

    app.use(bodyParser.urlencoded({
        extended: true
    }));

    app.use(bodyParser.json());
    app.use(helmet());
    app.disable('x-powered-by');


    /** AUTH */
    /**======================================================================== */
    app.post('/auth', async (request, response)  => {

        const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
        const username  = request.body.client_username;

        const token = "";
        try {
            token = await loginHandler(client_ip, username);
        }
        catch(ex) {
            log.error(`An error occurring during authentication for ${username} and IP:${client_ip}. Error: ${ex.message}`);
            return response.json({
                success: false,
                message: ex.message
            });
        }

        log.info(`Authenticated user:${username} with IP:${client_ip} and token:${token}`);

        return response.json({
            success: true,
            message: 'Authentication successful.',
            token: token
        });
        
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

        const res_auth = "";
        try {
            res_auth = await authenticator.chechToken(token, client_ip);
        }
        catch(ex) {
            log.error(`An error occurring during authentication for IP:${client_ip}. Error: ${ex.message}`);
            return response.json({
                success: false,
                message: ex.message
            });
        }

        if(res_auth) {
            log.info(`User authenticated with IP: ${client_ip}. Checking command validation...`);

            const res_cmd_validation = "";
            try {
                res_cmd_validation = await cmdValidation.commandParamValidation(action, topic, device, command);
            }
            catch(ex) {
                log.error(`An error occurring during command validation for IP:${client_ip}. Error: ${ex.message}`);
                    return response.json({
                        success: false,
                        message: ex.message
                });
            }
            
            if(res_cmd_validation) {
                log.info(`[SERVER] from IP:${client_ip} sended command: ${action}/${topic}/${device}/${command}.`);

                controller.controlDevice(topic, device, command, (status) => {
                    log.info(`[SERVER] ${action}/${topic}/${device}/${command} has status: ${status}.`);
                    if(status === '-1') {
                        return response.json({
                            success: true,
                            message: `The device was already ${command}`
                        });
                    }
                    else {
                        return response.json({
                            success: true,
                            message: `Device turned ${command}`
                        });
                    }
                });
            }
            else {
                return response.json({
                    success: false,
                    message: `An error occurring command validation.`
                });
            }
            
        }
        else {
            log.error(`An error occurring during auth validation for IP:${client_ip}. Error: ${ex.message}`);
            return response.json({
                success: false,
                message: 'Whoops! You are not authenticated!'
            });
        }
        
    });
    
    app.listen(config.server.SERVER_PORT, () => {log.info(`Server is listening on port: ${config.server.SERVER_PORT}`);});
};

start();