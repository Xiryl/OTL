const mqtt	 = require('mqtt');
const config = require('../config/config.json');
const log 	 = require('./../logger/logger');

/*
let getDeviceStatus = (topic, device, callback) => {
	const mqtt_client = mqtt.connect(config.MQTT.MQTT_BROKER_ADDRESS);	

	mqtt_client.on('connect', function () {

		log.debug(`[CONTROLLER]-[connect]-[${Date.now()}] Connected to ${config.MQTT.MQTT_BROKER_ADDRESS}`);

		mqtt_client.subscribe(`stat/${device}/+`);
		mqtt_client.publish	 (`cmnd/${device}/status`);
	
	});

	mqtt_client.on('message', function (topic, message) {

		if( topic === `stat/${device}/STATUS`) {
			log.warn('[STATUS] inside status');

			const actualPowerStatus = JSON.parse(message.toString()).Status.Power;

			log.warn(`[STATUS] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);

			callback(actualPowerStatus);
		}
	});
};
*/

let controlDevice = (topic, device, command, callback) => {

	const mqtt_client = mqtt.connect(config.MQTT.MQTT_BROKER_ADDRESS);	

	/** CONNECT HANDLER */
	mqtt_client.on('connect', function () {

		log.debug(`[CONTROLLER]-[connect]-[${Date.now()}] Connected to ${config.MQTT.MQTT_BROKER_ADDRESS}`);
	
		// subscribe to device status
		mqtt_client.subscribe(`stat/${device}/+`);
		mqtt_client.publish	 (`cmnd/${device}/status`);

		// send command to device
		mqtt_client.publish(`cmnd/${device}/power`, command);
	});

	/** DEVICE CALLBACK MESSAGE HANDLER */
	mqtt_client.on('message', function (topic, message) {

		if( topic === `stat/${device}/STATUS`) {

			const actualPowerStatus = JSON.parse(message.toString()).Status.Power;
			const newStatus 		= actualPowerStatus == 0 ? 'ON' : 'OFF';
			const convertedStatus 	= actualPowerStatus == 0 ? 'OFF' : 'ON';

			log.debug(`[STATUS] old power status : ${convertedStatus} [0] OFF - [1] ON`);
			log.debug(`[STATUS] new power status : ${newStatus} [0] OFF - [1] ON`);

			if(command === convertedStatus) {
				callback('-1'); // device was already on the same status
			}
			else{
				callback('1');	// status changed
			}		

			// stop connection
			mqtt_client.end();

			// notify agent
			slack.webhook({
				channel: config.slack.SLACK_CHANNEL,
				username: "VPS",
				icon_emoji: ":bulb:",
				text: `Authorized: topic/${device}/${command}.`
			}, function(err, res) {
				// nothing
			});
			
		}
		else if( topic === `stat/${device}/RESULT`) {
			/*//log.warn('[RESULT] inside status');
			//log.warn(`[RESULT] ${message.toString()}`);
			
			const actualPowerStatus = JSON.parse(message.toString()).POWER;
			log.debug(`[RESULT] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);
			//mqtt_client.publish(`cmnd/${device}/power`, actualPowerStatus);

			mqtt_client.end();
			callback(actualPowerStatus);
			//** skip this */
		}
		else if( topic === `stat/${device}/POWER`) {
			
		//	log.warn(`[POWER] ${message.toString()}`);
			
		}
	});
};

module.exports = { controlDevice };