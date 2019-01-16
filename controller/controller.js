const mqtt		= require('mqtt');
let express		= require('express')
let app			= express();

/** CONSTANTS */
/**==================================================================================== */
const SERVER_PORT = 8448;
const BROKER_HOST = '192.168.43.110';
const BROKER_ADDR = `mqtt://${BROKER_HOST}`;	

const allowed_devices = ['sonoff-1'];				// SONOFF IDENTIFIER

/** API */
/**==================================================================================== */
app.get('/', (req, res) => {
	
	mqtt_client.publish(`cmnd/${device}/power`, 'OFF');	
	res.send('Hello World with Express');

});

app.listen(SERVER_PORT, function () {
     console.log(`[SERVER] Starting server on port ${SERVER_PORT} ...`);
});


/** MQTT */
/**==================================================================================== */
const mqtt_client = mqtt.connect(BROKER_ADDR);	

/** CONNECTION HANDLER */
mqtt_client.on('connect', function () {

	console.log(`[CONTROLLER]-[connect]-[${Date.now()}] Client connected to ${broker}`);

	/** subscribe to sonoff */
	mqtt_client.subscribe(`stat/${device}/+`);
	mqtt_client.publish	(`cmnd/${device}/status`);

});

/** MESSAGE HANDLER */
mqtt_client.on('message', function (topic, message) {

	if( topic === `stat/${device}/STATUS`) {
		console.log('[STATUS] inside status');

		const actualPowerStatus = JSON.parse(message.toString()).Status.Power;
		const newStatus = actualPowerStatus == 0 ? 'ON' : 'OFF';

		console.log(`[STATUS] actual power status: ${actualPowerStatus} [0] OFF - [1] ON`);
		console.log(`[STATUS] new power status : ${newStatus} [0] OFF - [1] ON`);
		console.log('[STATUS] sending new power status ...');

		//client.publish(`cmnd/${device}/power`, newStatus);
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


