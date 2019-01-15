const mqtt   = require('mqtt');

const broker = 'mqtt://192.168.43.110';	// MQTT Broker HOST
const client = mqtt.connect(broker);	// MQTT Client
const device = 'sonoff-1';				// SONOFF IDENTIFIER

let state = 'OFF';

/** CONNECTION HANDLER */
client.on('connect', function () {

	console.log(`[CONTROLLER]-[connect]-[${Date.now()}] Client connected to ${broker}`);

	/** subscribe to sonoff */
	client.subscribe(`stat/${device}/+`);
	client.publish	(`cmnd/${device}/status`);

});

let first =  true;
client.on('message', function (topic, message) {

	if( topic === `stat/${device}/STATUS`) {
		console.log('[STATUS] inside status');

		const actualPowerStatus = JSON.parse(message.toString()).Status.Power;
		const newStatus = actualPowerStatus == 0 ? 'ON' : 'OFF';

		console.log(`[STATUS] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);
		console.log(`[STATUS] new power status : ${newStatus} [0] OFF - [1] ON`);
		console.log('[STATUS] sending new power status ...');

		client.publish(`cmnd/${device}/power`, newStatus);
		console.log('[STATUS] sended.');

	}
	else if( topic === `stat/${device}/RESULT`) {
		console.log('[RESULT] inside status');
		console.log(`[RESULT] ${message.toString()}`);
		//** skip this */
	}
	else if( topic === `stat/${device}/POWER`) {
		console.log('[POWER] inside status');
		console.log(`[POWER] ${message.toString()}`);

		//** skip this */
	}
});


