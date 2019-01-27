class InvalidTokenException extends Error {
	constructor() {
		super("Errore token non valido");
	}
}

class UserIpChangesException extends Error {
	constructor() {
		super("Ip utente cambiato");
	}
}

class MissingTokenException extends Error {
	constructor() {
		super("Token is not supplied");
	}
}
<<<<<<< HEAD
module.exports = 
	InvalidTokenException, 
	UserIpChangesException, 
	MissingTokenException 
=======

class InvalidActionForCommandException extends Error {
	constructor() {
		super("Invalid param action for command");
	}
}

class InvalidTopicForCommandException extends Error {
	constructor() {
		super("Invalid topic for command");
	}
}

class InvalidDeviceForCommandException extends Error {
	constructor() {
		super("Invalid device for command");
	}
}

class InvalidCommandForCommandException extends Error {
	constructor() {
		super("Invalid command for command");
	}
}

module.exports = 
	InvalidTokenException, 
	UserIpChangesException, 
	MissingTokenException,
	InvalidActionForCommandException,
	InvalidTopicForCommandException,
	InvalidDeviceForCommandException,
	InvalidCommandForCommandException
>>>>>>> master
;