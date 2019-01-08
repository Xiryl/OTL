const mqtt   = require('mqtt');

const broker = 'mqtt://192.168.1.109';	// MQTT Broker hostname/IP address
const client = mqtt.connect(broker);	// MQTT Client
const device = 'sonoff-1';				// Sonoff device identifier

let state = 'OFF';
let timer;

client.on('connect', function () {

	console.log(`${Date.now()} Client connected to ${broker}`);

	client.subscribe(`stat/${device}/+`);
	client.subscribe(`tele/${device}/+`);

	client.publish(`cmnd/${device}/status`);

	timer = setInterval(loop, 2000);
});

client.on('message', function (topic, message) {

	if (topic === `stat/${device}/POWER`) {
		state = message.toString();
	}

	console.log(`${Date.now()} RX ${topic} ${message}`);
});

function loop() {

	if (!client.connected) {
		return timer && timer.clearInterval();
	}

	let newState = state === 'OFF' ? 'ON' : 'OFF';

	client.publish(`cmnd/${device}/power`, newState);

	console.log(`${Date.now()} TX cmnd/${device}/power ${newState}`);
}