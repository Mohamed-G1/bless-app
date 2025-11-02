package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository

class SendSignatureUseCase(
    private val repository: RouteDetailsRepository
) {

//    suspend operator fun invoke(
//        file: File,
//        waybill: Int,
//        receiverName: String,
//    ): Resource<Any> {
//        return repository.sendUserSignature(file, waybill, receiverName)
//    }


}