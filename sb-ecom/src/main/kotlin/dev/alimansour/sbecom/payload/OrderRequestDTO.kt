package dev.alimansour.sbecom.payload

data class OrderRequestDTO(
    val addressId: Long,
    val pgName: String,
    val pgPaymentId: String,
    val pgStatus: String,
    val pgResponseMessage: String,
)
