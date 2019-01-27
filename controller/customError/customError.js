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
module.exports = 
	InvalidTokenException, 
	UserIpChangesException, 
	MissingTokenException 
;