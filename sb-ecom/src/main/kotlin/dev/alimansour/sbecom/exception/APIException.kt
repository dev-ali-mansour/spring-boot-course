package dev.alimansour.sbecom.exception

class APIException : RuntimeException {

    constructor()

    constructor(message: String) : super(message)

    private companion object {
        private const val serialVersionUID = 1L
    }
}
