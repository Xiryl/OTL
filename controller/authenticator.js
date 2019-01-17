/**https://medium.com/dev-bits/a-guide-for-adding-jwt-token-based-authentication-to-your-single-page-nodejs-applications-c403f7cf04f4 */

let     jwt     = require('jsonwebtoken');
const   config  = require('./config/config.js');

let chechToken = ( request, response, next ) =>  {
    console.log('[SERVER] inside chechToken');
    let token = request.headers['x-access-token'] || request.headers['authorization'];
    const client_ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress;
    
    if(token) {
        if (token.startsWith('Bearer ')) {
            /** Remove Bearer from string */ 
            token = token.slice(7, token.length);
        }

        console.log('[SERVER] received token:',token);

        /** verify the token with jwt */
        jwt.verify(token, config.private_key, ( error, decoded )  => {
            if(error) {
                return response.json({
                    success: false,
                    message: 'Invalid token.'
                });
            }

            if(decoded.ip === client_ip) {
                return next(request, response);
            }
            else {
                return response.json({
                    success: false,
                    message: 'Invalid token.'
                });
            }
        });
    }
    else {
        return response.json({
            success: false,
            message: 'Auth token is not supplied.'
        });
    }
};

/** export module */
module.exports = {
    chechToken : chechToken
};