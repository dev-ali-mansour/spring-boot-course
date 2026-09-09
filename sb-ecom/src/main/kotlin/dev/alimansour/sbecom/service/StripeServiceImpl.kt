package dev.alimansour.sbecom.service

import com.stripe.StripeClient
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import dev.alimansour.sbecom.payload.StripePaymentDTO
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class StripeServiceImpl(
    @Value($$"${stripe.secret}")
    private val secretKey: String
) : StripeService {
    private lateinit var client: StripeClient

    @PostConstruct
    fun init() {
        client = StripeClient(secretKey)
    }

    override fun createPaymentIntent(stripePaymentDTO: StripePaymentDTO): PaymentIntent {
        val params =
            PaymentIntentCreateParams.builder()
                .setAmount(stripePaymentDTO.amount)
                .setCurrency(stripePaymentDTO.currency)
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .build()
                )
                .build()

        val paymentIntent = client.v1().paymentIntents().create(params)

        return paymentIntent
    }
}