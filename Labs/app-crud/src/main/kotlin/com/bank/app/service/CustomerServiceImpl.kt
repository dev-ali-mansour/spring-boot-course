package com.bank.app.service

import com.bank.app.model.Customer
import com.bank.app.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CustomerServiceImpl(private val customerRepository: CustomerRepository) : CustomerService {
    override fun createCustomer(customer: Customer): Customer =
        customerRepository.save(customer)

    override fun getAllCustomers(): List<Customer> = customerRepository.findAll()

    override fun getCustomerById(id: Long): Customer =
        customerRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id $id not found")
            }

    override fun updateCustomer(customer: Customer, id: Long): Customer {
        if (!customerRepository.existsById(id))
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id $id not found")
        customer.id = id
        return customerRepository.save(customer)
    }

    override fun deleteCustomer(id: Long) {
        if (!customerRepository.existsById(id))
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id $id not found")

        customerRepository.deleteById(id)
    }
}