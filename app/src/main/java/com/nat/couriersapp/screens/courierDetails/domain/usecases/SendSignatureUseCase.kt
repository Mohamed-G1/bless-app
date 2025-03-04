package com.nat.couriersapp.screens.courierDetails.domain.usecases

import android.graphics.Bitmap
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository

class SendSignatureUseCase(
    private val repository: CourierDetailsRepository
) {

    suspend operator fun invoke(
        file: Bitmap,
        waybill: Int,
        receiverName: String,
    ): Resource<Any> {
        return repository.sendUserSignature(file, waybill, receiverName)
    }


}