const { createLogger, format, transports } = require('winston');

const filename ='./out-log.log';

const logger = createLogger({
  level: 'debug',
  format: format.combine(
    format.colorize(),
    format.timestamp({
      format: 'YYYY-MM-DD HH:mm:ss'
    }),
    format.printf(info => `[${info.timestamp}] [${info.level}]: ${info.message}`)
  ),
  transports: [
    new transports.Console({
      level: 'debug',
      format: format.combine(
        format.colorize(),
        format.timestamp({
          format: 'YYYY-MM-DD HH:mm:ss'
        }),
        format.printf(info => `[${info.timestamp}] [${info.level}]: ${info.message}`)
      )
    }),
    new transports.File({ filename })
  ]
});

logger.debug('Starting logger..');

module.exports = logger;