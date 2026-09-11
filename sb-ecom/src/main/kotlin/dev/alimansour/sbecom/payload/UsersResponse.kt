package dev.alimansour.sbecom.payload

data class UsersResponse(
    val content: List<UserDTO>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val lastPage: Boolean,
)
