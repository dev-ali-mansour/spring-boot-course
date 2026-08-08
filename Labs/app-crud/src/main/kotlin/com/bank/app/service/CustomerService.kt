package com.bank.app.service

import com.bank.app.model.Customer

interface CustomerService {
    fun createCustomer(customer: Customer): Customer
    fun getAllCustomers(): List<Customer>
    fun getCustomerById(id: Long): Customer
    fun updateCustomer(customer: Customer,id:Long): Customer
    fun deleteCustomer(id: Long)
}
