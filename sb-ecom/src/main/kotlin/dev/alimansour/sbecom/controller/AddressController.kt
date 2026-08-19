package dev.alimansour.sbecom.controller

import dev.alimansour.sbecom.exception.APIException
import dev.alimansour.sbecom.payload.AddressDTO
import dev.alimansour.sbecom.service.AddressService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AddressController(private val addressService: AddressService) {

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @PostMapping("/addresses")
    fun createAddress(@Validated @RequestBody addressDTO: AddressDTO): ResponseEntity<AddressDTO> {
        val savedAddress: AddressDTO = addressService.createAddress(addressDTO)
        return ResponseEntity(savedAddress, HttpStatus.CREATED)
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @GetMapping("/addresses")
    fun getAddresses(): ResponseEntity<List<AddressDTO>> {
        val addressList: List<AddressDTO> = addressService.getAddresses()
        return ResponseEntity(addressList, HttpStatus.OK)
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @GetMapping("/addresses/{id}")
    fun getAddressesById(@PathVariable id: Long): ResponseEntity<AddressDTO> {
        val addressDTO = addressService.getAddressById(id)
        return ResponseEntity(addressDTO, HttpStatus.OK)
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @GetMapping("/users/addresses")
    fun getUserAddresses(): ResponseEntity<List<AddressDTO>> {
        val addressList: List<AddressDTO> = addressService.getUserAddresses()
        return ResponseEntity(addressList, HttpStatus.OK)
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @PutMapping("/addresses/{id}")
    fun updateAddress(
        @PathVariable id: Long,
        @Validated @RequestBody addressDTO: AddressDTO
    ): ResponseEntity<AddressDTO> {
        if (addressDTO.id != null && addressDTO.id != id) {
            throw APIException(message = "Resource ID mismatch: URL path specifies id $id, but the request body contains ${addressDTO.id}")
        }

        val updatedAddress = addressService.updateAddress(id, addressDTO)
        return ResponseEntity(updatedAddress, HttpStatus.OK)
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @DeleteMapping("/addresses/{id}")
    fun deleteAddress(@PathVariable id: Long): ResponseEntity<AddressDTO> =
        ResponseEntity(addressService.deleteAddress(id), HttpStatus.OK)
}