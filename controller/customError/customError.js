class InvalidTokenException extends Error {
	constructor() {
		super("Invalid token.");
	}
}

class UserIpChangesException extends Error {
	constructor() {
		super("The token is no longer valid after your ip change.");
	}
}

class MissingTokenException extends Error {
	constructor() {
		super("Auth token is not supplied.");
	}
}

class InvalidActionForCommandException extends Error {
	constructor() {
		super("Invalid parameter {action}.");
	}
}

class InvalidTopicForCommandException extends Error {
	constructor() {
		super("Invalid parameter {topic}.");
	}
}

class InvalidDeviceForCommandException extends Error {
	constructor() {
		super("Invalid parameter {device}.");
	}
}

class InvalidCommandForCommandException extends Error {
	constructor() {
		super("Invalid parameter {command}.");
	}
}

class UserNotAllowedException extends Error {
	constructor() {
		super("The specific user are not allowed to loggin in.");
	}
}

module.exports = {
	InvalidTokenException, 
	UserIpChangesException, 
	MissingTokenException,
	InvalidActionForCommandException,
	InvalidTopicForCommandException,
	InvalidDeviceForCommandException,
	InvalidCommandForCommandException,
	UserNotAllowedException
}
;