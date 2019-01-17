const mqtt	 = require('mqtt');
const config = require('../config/config.json');

let controlDevice = (topic, device, command) => {
	const mqtt_client = mqtt.connect(config.MQTT.MQTT_BROKER_ADDRESS);	

	mqtt_client.on('connect', function () {

		console.log(`[CONTROLLER]-[connect]-[${Date.now()}] Connected to ${config.MQTT.MQTT_BROKER_ADDRESS}`);
	
		/** subscribe to sonoff */
		mqtt_client.subscribe(`stat/${device}/+`);

		//mqtt_client.publish	 (`cmnd/${device}/status`);
		mqtt_client.publish(`cmnd/${device}/power`, command);
	
	});

	/** MESSAGE HANDLER */
	mqtt_client.on('message', function (topic, message) {

		if( topic === `stat/${device}/STATUS`) {
			console.log('[STATUS] inside status');

			const actualPowerStatus = JSON.parse(message.toString()).Status.Power;
			const newStatus = actualPowerStatus == 0 ? 'ON' : 'OFF';

			console.log(`[STATUS] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);
			console.log(`[STATUS] new power status : ${newStatus} [0] OFF - [1] ON`);
		}
		else if( topic === `stat/${device}/RESULT`) {
			console.log('[RESULT] inside status');
			console.log(`[RESULT] ${message.toString()}`);
			const actualPowerStatus = JSON.parse(message.toString()).POWER;
			console.log(`[RESULT] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);
			mqtt_client.publish(`cmnd/${device}/power`, actualPowerStatus);
			mqtt_client.end();
			//** skip this */
		}
		else if( topic === `stat/${device}/POWER`) {
			console.log('[POWER] inside power');
			console.log(`[POWER] ${message.toString()}`);
			//** skip this */
		}
	});
};

module.exports = controlDevice;