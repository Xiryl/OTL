let jwt      = require('jsonwebtoken');
const config = require('../../config/config.json');

let loginHandler = (request, response) => {

    const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
    const username  = request.body.client_username;
    
    if(config.jwt.JWT_ALLOWED_USERS.includes(username)) {

        /** create token */
        let client_token = jwt.sign({client_username: username, ip: client_ip}, config.jwt.JWT_PRIVATE_KEY);

        console.log(`[SERVER] Autehticated user '${username}' with token '${client_token}'.\nIP: ${client_ip}`);

        /** send token */
        response.json({
            success: true,
            message: 'Authentication successful.',
            token: client_token
        });
    }
    else
    {
        console.log(`[SERVER] Autehtication failed for user '${username}'.\nIP: ${client_ip}`);

        response.json({
            success: false,
            message: 'Authentication failed.'
        });
    }
}

module.exports = loginHandler;