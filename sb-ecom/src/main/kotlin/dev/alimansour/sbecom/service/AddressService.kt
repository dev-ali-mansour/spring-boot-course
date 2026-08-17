package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.payload.AddressDTO

interface AddressService {
    fun createAddress(addressDTO: AddressDTO): AddressDTO
}