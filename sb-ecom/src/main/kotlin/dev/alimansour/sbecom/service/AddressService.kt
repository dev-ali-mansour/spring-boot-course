package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.AddressDTO

interface AddressService {
    fun createAddress(addressDTO: AddressDTO): AddressDTO
    fun getAddresses(): List<AddressDTO>
    fun getAddressById(id: Long): AddressDTO
}