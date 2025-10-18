package com.nat.greco.screens.clientDetails.domain.usecases

import com.nat.greco.screens.clientDetails.domain.repository.CourierDetailsRepository

class SendSignatureUseCase(
    private val repository: CourierDetailsRepository
) {

//    suspend operator fun invoke(
//        file: File,
//        waybill: Int,
//        receiverName: String,
//    ): Resource<Any> {
//        return repository.sendUserSignature(file, waybill, receiverName)
//    }


}