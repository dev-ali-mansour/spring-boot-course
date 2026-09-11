package dev.alimansour.sbecom.payload

data class UserDTO(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val roles: List<String>,
    val addresses: List<AddressDTO>,
    val cart: CartDTO?,
)
