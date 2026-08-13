package com.bank.app

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contacts")
class ContactController {

    @GetMapping
    fun getContacts(): String {
        return "Returning all contacts"
    }

    @PostMapping
    fun addContact(): String {
        return "New contact added!"
    }

    @DeleteMapping("/{id}")
    fun deleteContact(@PathVariable id: Int): String {
        return "Contact $id deleted!"
    }

    @GetMapping("/public/info")
    fun publicInfo(): String {
        return "This is a public endpoint"
    }
}
