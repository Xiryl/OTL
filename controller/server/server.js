const express       = require('express');
const bodyParser    = require('body-parser');
let jwt             = require('jsonwebtoken');
let config          = require('../config/config');
let authenticator   = require('../authenticator');
let loginHandler    = require('./server-handlers/login-handler');

/** const */
const SERVER_PORT = 5011;

let apiHandler = (request, response) => {
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

    /** AUTH HANDLER */ 
    app.post('/auth', (req, res)  => {
        console.log('[SERVER] inside /auth');
        loginHandler(req, res);
    });

    /** API CALL HANDLER */ 
    app.get('/on', (req, res) => {
        authenticator.chechToken(req, res, apiHandler);
    });
    
    app.listen(SERVER_PORT, () => console.log(`Server is listening on port: ${SERVER_PORT}`));
};

start();