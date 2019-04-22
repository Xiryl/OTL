const config = require('./../config/config');
const customErr = require('./../customError/customError');
/*
	check params validation. The command have this format:
		.../action/topic/device/command
	the action must be the string contained inside config/MQTT_ALLOWED_ACTION_FOR_COMMAND
*/
let commandParamValidation = async (action, topic, device, command) => {
	if(action === config.MQTT.MQTT_ALLOWED_ACTION_FOR_COMMAND) {
		if(config.MQTT.MQTT_ALLOWED_DEVICES.includes(device)) {
			if(config.MQTT.MQTT_ALLOWED_COMMANDS.includes(command)) {
				return true;
			}
			else {
				throw new customErr.InvalidCommandForCommandException();
			}
		}
		else{
			throw new customErr.InvalidDeviceForCommandException();
		}
	} 
	else {
		throw new customErr.InvalidActionForCommandException();
	}
};

module.exports = { commandParamValidation };