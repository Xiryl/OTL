/**https://medium.com/dev-bits/a-guide-for-adding-jwt-token-based-authentication-to-your-single-page-nodejs-applications-c403f7cf04f4 */
let jwt         = require('jsonwebtoken');
const config    = require('./config/config.json');
let log         = require('./logger/logger');
let customError = require('./customError/customError');

let chechToken = async (token, client_ip, next) =>  {

    if(token) {
        if (token.startsWith('Bearer ')) {

            /** Remove Bearer from string */ 
            token = token.slice(7, token.length);
        }
        
        log.debug(`Received request from IP:${client_ip} and token:${token}`);

        /** verify the token with jwt */
        try {
            const decoded = jwt.verify(token, config.jwt.JWT_PRIVATE_KEY);
            if(decoded.ip === client_ip) {
                log.debug(`Validating user with IP:${client_ip} and token:${token}`);

                return true;
            }
            else {
                log.error(`Error, invalid 'client_ip' with same token from IP:${client_ip} and token:${token}`);
                throw new customError.UserIpChangesException;
            }
        }
        catch(ex) {
            log.error(`Error, invalid token from IP:${client_ip} and token:${token}`);
            throw new customError.InvalidTokenException;
        }
        
    }
    else {
        log.error(`Error, Auth token is not supplied from IP:${client_ip}.`);
        throw new customError.MissingTokenException;
    }
};

/** export module */
module.exports = {
    chechToken
};