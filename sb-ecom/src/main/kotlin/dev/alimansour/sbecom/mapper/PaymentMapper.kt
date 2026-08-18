package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.model.Payment
import dev.alimansour.sbecom.payload.PaymentDTO

fun Payment.toDTO(): PaymentDTO =
    PaymentDTO(
        id = this.id,
        paymentMethod = this.paymentMethod,
        pgPaymentId = this.pgPaymentId,
        pgStatus = this.pgStatus,
        pgResponseMessage = this.pgResponseMessage,
        pgName = this.pgName,
    )

fun PaymentDTO.toEntity(): Payment =
    Payment(
        id = this.id,
        paymentMethod = this.paymentMethod,
        pgPaymentId = this.pgPaymentId,
        pgStatus = this.pgStatus,
        pgResponseMessage = this.pgResponseMessage,
        pgName = this.pgName,
    )