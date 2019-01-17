const express       = require('express');
const bodyParser    = require('body-parser');
let jwt             = require('jsonwebtoken');
let config          = require('../config/config');
let authenticator   = require('../authenticator');
let loginHandler    = require('./server-handlers/login-handler');

/** const */
const SERVER_PORT = 5011;

let APISendCommandToMQTTBroker = (req, res, topic, dev, cmd) => {
   console.log('yooo');
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
        authenticator.chechToken(request, response, (err, data) => {
            APISendCommandToMQTTBroker(request, response, request.params.topic, request.params.device, request.params.command);
        });
    });
    
    app.listen(SERVER_PORT, () => console.log(`Server is listening on port: ${SERVER_PORT}`));
};

start();