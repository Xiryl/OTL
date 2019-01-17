const express       = require('express');
const bodyParser    = require('body-parser');
let jwt             = require('jsonwebtoken');
let config          = require('../config/config');
let authenticator   = require('../authenticator');
let loginHandler    = require('./server-handlers/login-handler');

/** const */
const SERVER_PORT = 5011;

let APISendCommandToMQTTBroker = (request, response) => {
    //OK
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
    app.get('/:device/:command', (request, response) => {
        console.log(request.params.device);
        console.log(request.params.command);
        //authenticator.chechToken(req, res, APISendCommandToMQTTBroker);
    });
    
    app.listen(SERVER_PORT, () => console.log(`Server is listening on port: ${SERVER_PORT}`));
};

start();