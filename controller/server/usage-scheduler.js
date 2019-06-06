const mongoist = require('mongoist');
const config        = require('../config/config.json');
let controller      = require('../mqtt-controller/controller');

/** DB */
const db = mongoist(`mongodb://localhost:27017/otl`);


let makeCheck = async () => {
    for(let i = 0; i < config.MQTT.MQTT_ALLOWED_DEVICES.length; i++) {
        console.log('processing' + config.MQTT.MQTT_ALLOWED_DEVICES[i]);
        let status = await controller.getDeviceStatus('', config.MQTT.MQTT_ALLOWED_DEVICES[i]);
        console.log('status' + status );
        const doc = await db.otlstatus.save({device: config.MQTT.MQTT_ALLOWED_DEVICES[i], status: status});
    };
};


makeCheck();