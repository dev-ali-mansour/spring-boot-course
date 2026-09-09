package dev.alimansour.sbecom.service
import com.stripe.model.PaymentIntent
import dev.alimansour.sbecom.payload.StripePaymentDTO

interface StripeService {
    fun createPaymentIntent(stripePaymentDTO: StripePaymentDTO): PaymentIntent
}