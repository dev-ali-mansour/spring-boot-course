package dev.alimansour.sbecom.payload

data class APIResponse(
    val status: Boolean = false,
    val errors: List<String> = emptyList(),
)
