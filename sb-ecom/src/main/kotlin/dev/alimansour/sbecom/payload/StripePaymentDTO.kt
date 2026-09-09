package dev.alimansour.sbecom.payload

import dev.alimansour.sbecom.model.Address

data class StripePaymentDTO(
    val amount: Long,
    val currency: String,
    val email: String,
    val name: String,
    val address: Address,
    val description: String,
    val metadata: Map<String, String>,
)
