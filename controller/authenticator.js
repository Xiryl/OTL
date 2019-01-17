/**https://medium.com/dev-bits/a-guide-for-adding-jwt-token-based-authentication-to-your-single-page-nodejs-applications-c403f7cf04f4 */

let     jwt     = require('jsonwebtoken');
const   config  = require('./config/config.js');

let chechToken = ( request, response, next ) =>  {

    let token = request.headers['x-access-token'] || request.headers['authorization'];
    if (token.startsWith('Bearer ')) {
        /** Remove Bearer from string */ 
        token = token.slice(7, token.length);
    }

    if(token) {
        
        /** verify the token with jwt */
        jwt.verify(token, config.private_key, ( error, decoded )  => {
            if(error) {
                return response.json({
                    success: false,
                    message: 'Invalid token.'
                });
            }

            request.decoded = decoded;
            next();
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