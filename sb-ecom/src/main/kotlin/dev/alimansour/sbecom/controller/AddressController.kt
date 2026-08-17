package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.payload.AddressDTO
import dev.alimansour.sbecom.service.AddressService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AddressController(private val addressService: AddressService) {

    @PostMapping("/addresses")
    fun createAddress(@Validated @RequestBody addressDTO: AddressDTO): ResponseEntity<AddressDTO> {
        val savedAddress: AddressDTO = addressService.createAddress(addressDTO)
        return ResponseEntity(savedAddress, HttpStatus.CREATED)
    }

    @GetMapping("/addresses")
    fun getAddresses(): ResponseEntity<List<AddressDTO>> {
        val addressList: List<AddressDTO> = addressService.getAddresses()
        return ResponseEntity(addressList, HttpStatus.OK)
    }

    @GetMapping("/addresses/{id}")
    fun getAddressesById(@PathVariable id: Long): ResponseEntity<AddressDTO> {
        val addressDTO = addressService.getAddressById(id)
        return ResponseEntity(addressDTO, HttpStatus.OK)
    }
}