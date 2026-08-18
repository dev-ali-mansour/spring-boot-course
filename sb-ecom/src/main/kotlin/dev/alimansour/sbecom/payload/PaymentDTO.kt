package dev.alimansour.sbecom.payload

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class PaymentDTO(
    val id: Long? = null,
    @NotBlank
    @Size(min = 4, message = "Payment method must contain at least 4 characters")
    val paymentMethod: String,
    val pgPaymentId: String,
    val pgStatus: String,
    val pgResponseMessage: String,
    val pgName: String,
)
