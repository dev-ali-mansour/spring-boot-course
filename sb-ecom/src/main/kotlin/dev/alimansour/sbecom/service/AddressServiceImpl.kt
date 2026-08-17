package dev.alimansour.sbecom.service

import dev.alimansour.sbecom.exception.ResourceNotFoundException
import dev.alimansour.sbecom.mapper.toDTO
import dev.alimansour.sbecom.mapper.toEntity
import dev.alimansour.sbecom.payload.AddressDTO
import dev.alimansour.sbecom.repository.AddressRepository
import dev.alimansour.sbecom.repository.UserRepository
import dev.alimansour.sbecom.util.AuthUtil
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl(
    private val addressRepository: AddressRepository,
    private val userRepository: UserRepository,
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

    override fun getUserAddresses(): List<AddressDTO> {
        val user = authUtil.loggedInUser()
        return user.addresses.map { it.toDTO() }
    }

    override fun updateAddress(id: Long, addressDTO: AddressDTO): AddressDTO {
        val existedAddress = addressRepository.findById(id).orElseThrow {
            ResourceNotFoundException(resourceName = "Address", field = "id", fieldId = id)
        }

        val address = addressDTO.toEntity().apply {
            this.id = id
            this.user = existedAddress.user
        }

        val updatedAddress = addressRepository.save(address)

        existedAddress.user?.let { user ->
            user.addresses.removeIf { it.id == id }
            user.addresses.add(updatedAddress)
            userRepository.save(user)
        }
        return updatedAddress.toDTO()
    }

    override fun deleteAddress(id: Long): AddressDTO {
        val address = addressRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(resourceName = "Address", field = "id", fieldId = id) }

        addressRepository.delete(address)
        return address.toDTO()
    }
}