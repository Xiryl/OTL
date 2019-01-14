const mqtt   = require('mqtt');

const broker = 'mqtt://192.168.43.110';	// MQTT Broker HOST
const client = mqtt.connect(broker);	// MQTT Client
const device = 'sonoff-1';				// SONOFF IDENTIFIER

let state = 'OFF';

/** CONNECTION HANDLER */
client.on('connect', function () {

	console.log(`[CONTROLLER]-[connect]-[${Date.now()}] Client connected to ${broker}`);

	// subscribe to get sonoff status
	client.subscribe(`stat/${device}/+`);

	client.publish(`cmnd/${device}/status`);
});

let first =  true;
client.on('message', function (topic, message) {


	if(first) {
		const x = JSON.parse(message.toString()).Status.Power;
		console.log('[message]', JSON.parse(message.toString()));
		console.log('[stat]', x);
		console.log('[topic]', topic.toString());
	
	// control if message received is for actual sonoff
	/*if (topic === `stat/${device}/STATUS`) {
	}*/
		console.log(`${Date.now()} Received ${topic} ${message}`);

		let newState;
		console.log('** stato:', state);
		if(x === 0) {
			newState = 'ON';
			console.log('on');
		}
		else{
			newState = 'OFF';
			console.log('off');
		}

		console.log('** stato:', newState);
		client.publish(`cmnd/${device}/power`, newState);
		console.log(`${Date.now()} TX cmnd/${device}/power ${newState}`);
		first=false;
}
else {
	console.log('------\n\n');
	return;
}
	
});