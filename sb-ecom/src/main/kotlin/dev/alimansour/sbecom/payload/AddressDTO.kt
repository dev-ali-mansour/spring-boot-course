package dev.alimansour.sbecom.payload

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AddressDTO(
    var id: Long? = null,

    @NotBlank
    @Size(min = 5, message = "Street name must be at least 5 characters")
    var street: String,

    @NotBlank
    @Size(min = 5, message = "Building name must be at least 5 characters")
    var buildingName: String,

    @NotBlank
    @Size(min = 4, message = "City name must be at least 4 characters")
    var city: String,

    @NotBlank
    @Size(min = 2, message = "State name must be at least 2 characters")
    var state: String,

    @NotBlank
    @Size(min = 2, message = "Country name must be at least 2 characters")
    var country: String,

    @NotBlank
    @Size(min = 5, message = "Pincode must be at least 5 characters")
    var pinCode: String,
)
