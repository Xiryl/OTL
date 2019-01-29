const mqtt	 = require('mqtt');
const config = require('../config/config.json');
const log 	 = require('./../logger/logger');

let getDeviceStatus = (topic, device, callback) => {
	const mqtt_client = mqtt.connect(config.MQTT.MQTT_BROKER_ADDRESS);	

	mqtt_client.on('connect', function () {

		log.debug(`[CONTROLLER]-[connect]-[${Date.now()}] Connected to ${config.MQTT.MQTT_BROKER_ADDRESS}`);

		/** subscribe to sonoff */
		mqtt_client.subscribe(`stat/${device}/+`);
		mqtt_client.publish	 (`cmnd/${device}/status`);
	
	});

	/** MESSAGE HANDLER */
	mqtt_client.on('message', function (topic, message) {

		if( topic === `stat/${device}/STATUS`) {
			log.warn('[STATUS] inside status');

			const actualPowerStatus = JSON.parse(message.toString()).Status.Power;

			log.warn(`[STATUS] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);

			callback(actualPowerStatus === 0 ? 'OFF' : 'ON');
		}
	});
};

let controlDevice = (topic, device, command) => {
	const mqtt_client = mqtt.connect(config.MQTT.MQTT_BROKER_ADDRESS);	

	mqtt_client.on('connect', function () {

		log.debug(`[CONTROLLER]-[connect]-[${Date.now()}] Connected to ${config.MQTT.MQTT_BROKER_ADDRESS}`);
	
		/** subscribe to sonoff */
		mqtt_client.subscribe(`stat/${device}/+`);

		//NO: mqtt_client.publish	 (`cmnd/${device}/status`);

		mqtt_client.publish(`cmnd/${device}/power`, command);
	
	});

	/** MESSAGE HANDLER */
	mqtt_client.on('message', function (topic, message) {

		if( topic === `stat/${device}/STATUS`) {
			log.warn('[STATUS] inside status');

			const actualPowerStatus = JSON.parse(message.toString()).Status.Power;
			const newStatus = actualPowerStatus == 0 ? 'ON' : 'OFF';

			log.warn(`[STATUS] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);
			log.warn(`[STATUS] new power status : ${newStatus} [0] OFF - [1] ON`);
		}
		else if( topic === `stat/${device}/RESULT`) {
			log.warn('[RESULT] inside status');
			log.warn(`[RESULT] ${message.toString()}`);
			const actualPowerStatus = JSON.parse(message.toString()).POWER;
			log.warn(`[RESULT] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);
			mqtt_client.publish(`cmnd/${device}/power`, actualPowerStatus);
			mqtt_client.end();
			//** skip this */
		}
		else if( topic === `stat/${device}/POWER`) {
			log.warn('[POWER] inside power');
			log.warn(`[POWER] ${message.toString()}`);
			//** skip this */
		}
	});
};

module.exports = {controlDevice, getDeviceStatus};