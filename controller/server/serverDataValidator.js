const config = require('./../config/config');

/*
	check params validation. The command have this format:
		.../action/topic/device/command
	the action must be the string contained inside config/MQTT_ALLOWED_ACTION_FOR_COMMAND
*/
let commandParamValidation = await (action, topic, device, command) {
	if(action === config.MQTT_ALLOWED_ACTION_FOR_COMMAND) {
		if(config.MQTT_ALLOWED_TOPICS.includes(topic)) {
			if(config.MQTT_ALLOWED_DEVICES.includes(device)) {
				if(config.MQTT_ALLOWED_COMMANDS.includes(command)) {
					return true;
				}
				else {
					throw new InvalidCommandForCommandException();
				}
			}
			else{
				throw new InvalidDeviceForCommandException();
			}
		}
		else {
			throw new InvalidTopicForCommandException();
		}
	}
	else {
		throw new InvalidActionForCommandException();
	}
};

module.exports = { commandParamValidation };