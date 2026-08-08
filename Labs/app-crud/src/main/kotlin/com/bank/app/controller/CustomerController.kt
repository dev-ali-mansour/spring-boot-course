package com.bank.app.controller

import com.bank.app.model.Customer
import com.bank.app.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping(("/api/customers"))
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    fun createCustomer(@RequestBody customer: Customer): ResponseEntity<Customer> =
        ResponseEntity.ok(customerService.createCustomer(customer))

    @GetMapping
    fun getAllCustomers(): ResponseEntity<List<Customer>> =
        ResponseEntity.ok(customerService.getAllCustomers())

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: Long): ResponseEntity<Customer> = runCatching {
        ResponseEntity.ok(customerService.getCustomerById(id))
    }.getOrElse { t ->
        if (t is ResponseStatusException) ResponseEntity(t.statusCode)
        else ResponseEntity.internalServerError().build()
    }

    @PutMapping("/{id}")
    fun updateCustomer(@RequestBody customer: Customer, @PathVariable id: Long): ResponseEntity<Customer> {
        return runCatching {
            ResponseEntity.ok(customerService.updateCustomer(customer, id))
        }.getOrElse { t ->
            if (t is ResponseStatusException) ResponseEntity.status(t.statusCode).build()
            else ResponseEntity.internalServerError().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<String> {
        return runCatching {
            customerService.deleteCustomer(id)
            return ResponseEntity.ok("Customer deleted successfully!")
        }.getOrElse { t ->
            if (t is ResponseStatusException) ResponseEntity.status(t.statusCode).body(t.reason)
            else ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t.message)
        }
    }
}
