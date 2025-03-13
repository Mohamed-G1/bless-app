package com.nat.couriersapp.screens.courierDetails.domain.usecases

import android.graphics.Bitmap
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository
import okhttp3.MultipartBody
import java.io.File

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