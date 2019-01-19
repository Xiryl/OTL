let jwt      = require('jsonwebtoken');
const config = require('../../config/config.json');
let log      = require('./../../logger/logger');

let loginHandler = (request, response) => {

    const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
    const username  = request.body.client_username;
    
    if(config.jwt.JWT_ALLOWED_USERS.includes(username)) {

        /** create token */
        let client_token = jwt.sign({client_username: username, ip: client_ip}, config.jwt.JWT_PRIVATE_KEY);

        log.info(`Autehticated user '${username}' with token '${client_token}' & IP: ${client_ip}`)

        /** send token */
        response.json({
            success: true,
            message: 'Authentication successful.',
            token: client_token
        });
    }
    else
    {
        log.error(`Failed to authenticate user '${username}' with IP: ${client_ip}`);

        response.json({
            success: false,
            message: 'Authentication failed.'
        });
    }
}

module.exports = loginHandler;