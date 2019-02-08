let jwt      = require('jsonwebtoken');
const config = require('../../config/config.json');
let log      = require('./../../logger/logger');
let customex = require('./../../customError/customError')

let loginHandler = async (client_ip, username) => {
    
    if(config.jwt.JWT_ALLOWED_USERS.includes(username)) {

        /** create token , { expiresIn: config.jwt.JWT_TOKEN_EXPIRATION } */
        let client_token = jwt.sign({client_username: username, ip: client_ip}, config.jwt.JWT_PRIVATE_KEY);

        log.info(`Autehticated user '${username}' with token '${client_token}' & IP: ${client_ip}`)

        return client_token;
    }
    else
    {
        log.error(`Failed to authenticate user '${username}' with IP: ${client_ip}`);
        throw new customex.UserNotAllowedException;
    }
}

module.exports = loginHandler;