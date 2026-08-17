package dev.alimansour.sbecom.mapper

import dev.alimansour.sbecom.model.Address
import dev.alimansour.sbecom.payload.AddressDTO

fun Address.toDTO(): AddressDTO = AddressDTO(
    id = this.id,
    street = this.street,
    buildingName = this.buildingName,
    city = this.city,
    state = this.state,
    country = this.country,
    pinCode = this.pinCode,
)

fun AddressDTO.toEntity(): Address = Address(
    id = this.id,
    street = this.street,
    buildingName = this.buildingName,
    city = this.city,
    state = this.state,
    country = this.country,
    pinCode = this.pinCode,
)