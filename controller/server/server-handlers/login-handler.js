let jwt = require('jsonwebtoken');

/** const */
const TOKEN_EXPIRATION = '1h';

let loginHandler = (request, response) => {

    const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
    const username  = request.body.client_username;
    
    if(config.allowed_clients.includes(username)) {

        /** create token */
        let client_token = jwt.sign({client_username: username, ip: client_ip}, config.private_key);

        console.log(`[SERVER] Autehticated user '${username}' with token '${client_token}', experies in ${TOKEN_EXPIRATION}.\nIP: ${client_ip}`);

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