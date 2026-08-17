package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.mapper.toEntity
import dev.alimansour.sbecom.payload.AddressDTO
import dev.alimansour.sbecom.repository.AddressRepository
import dev.alimansour.sbecom.util.AuthUtil
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl(
    private val addressRepository: AddressRepository,
    private val authUtil: AuthUtil,
) : AddressService {
    override fun createAddress(addressDTO: AddressDTO): AddressDTO {
        val user = authUtil.loggedInUser()
        val address = addressDTO.toEntity()
        val addressList = user.addresses
        addressList.add(address)
        address.user = user

        val savedAddress = addressRepository.save(address)

        return savedAddress.toDTO()
    }

    override fun getAddresses(): List<AddressDTO> = addressRepository.findAll().map { it.toDTO() }

    override fun getAddressById(id: Long): AddressDTO {
        val addressDTO = addressRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(resourceName = "Address", field = "id", fieldId = id) }

        return addressDTO.toDTO()
    }

}