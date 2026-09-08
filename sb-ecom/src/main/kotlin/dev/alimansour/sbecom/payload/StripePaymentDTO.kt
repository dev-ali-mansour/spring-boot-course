package dev.alimansour.sbecom.payload

data class StripePaymentDTO(
    val amount: Long,
    val currency: String,
)
