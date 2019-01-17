const express       = require('express');
const bodyParser    = require('body-parser');
let authenticator   = require('../authenticator');
let loginHandler    = require('./server-handlers/login-handler');

const mqttDevices   = require('./../mqtt-controller/devices');
const mqttTopics    = require('./../mqtt-controller/topics');
const mqttCommands  = ['ON', 'OFF'];

/** const */
const SERVER_PORT = 5011;

let APISendCommandToMQTTBroker = (req, res, topic, dev, cmd) => {

    // check if topic exists
    if(mqttTopics.topics.includes(topic)) {
        // check if devices is on white list
        if(mqttDevices.allowed_devices.includes(dev)) {
            //TODO: send command   
            
            return res.json({
                allowed: true,
                message: `Command ${cmd} sended to ${topic}/${dev} successfully.`
            });
        }
    }
    
}

let start = () => {
    // Todo: make more secure like https://expressjs.com/it/advanced/best-practice-security.html

    let app = express();
    let handler = null;

    app.use(bodyParser.urlencoded({
        extended: true
    }));

    app.use(bodyParser.json());

    /** PAI auth handler */ 
    app.post('/auth', (request, response)  => {
        console.log('[SERVER] inside /auth');
        loginHandler(request, response);
    });

    /** API call handler */ 
    app.get('/:topic/:device/:command', (request, response) => {
        authenticator.chechToken(request, response, ( data ) => {
            if(!data) {
                return response.json({
                    allowed: true,
                    message: 'Something goes wrong. Retry.'
                });
            }
            else{
                APISendCommandToMQTTBroker(request, response, request.params.topic, request.params.device, request.params.command); 
            }
        });
    });
    
    app.listen(SERVER_PORT, () => console.log(`Server is listening on port: ${SERVER_PORT}`));
};

start();