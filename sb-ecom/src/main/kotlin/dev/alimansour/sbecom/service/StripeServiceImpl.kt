package dev.alimansour.sbecom.service

import com.stripe.StripeClient
import com.stripe.model.Customer
import com.stripe.model.PaymentIntent
import com.stripe.param.CustomerCreateParams
import com.stripe.param.CustomerSearchParams
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
        val customer: Customer? = findOrCreateCustomer(stripePaymentDTO)

        val params =
            PaymentIntentCreateParams.builder()
                .setAmount(stripePaymentDTO.amount)
                .setCurrency(stripePaymentDTO.currency)
                .setCustomer(customer?.id)
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .build()
                )
                .build()

        val paymentIntent = client.v1().paymentIntents().create(params)

        return paymentIntent
    }

    private fun findOrCreateCustomer(stripePaymentDTO: StripePaymentDTO): Customer? {
        val searchParams =
            CustomerSearchParams.builder()
                .setQuery("email:'${stripePaymentDTO.email}'")
                .build()
        val stripeSearchResult = client.v1().customers().search(searchParams)
        if (stripeSearchResult.data.isEmpty()) {
            val params =
                CustomerCreateParams.builder()
                    .setName(stripePaymentDTO.name)
                    .setEmail(stripePaymentDTO.email)
                    .setAddress(
                        CustomerCreateParams.Address.builder()
                            .setLine1(stripePaymentDTO.address.street)
                            .setCity(stripePaymentDTO.address.city)
                            .setState(stripePaymentDTO.address.state)
                            .setPostalCode(stripePaymentDTO.address.pinCode)
                            .setCountry(stripePaymentDTO.address.country)
                            .build()
                    )
                    .build()
            return client.v1().customers().create(params)
        } else {
            return stripeSearchResult.data[0]
        }
    }
}