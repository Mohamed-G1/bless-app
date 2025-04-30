package com.nat.greco.screens.courierDetails.domain.usecases

import com.nat.greco.screens.courierDetails.domain.repository.CourierDetailsRepository

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