package com.nat.couriersapp.base.network

import android.graphics.Bitmap
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredRequest
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.RefusalReasonsResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.StatusNotDeliveredResponse
import com.nat.couriersapp.screens.home.domain.models.HomeResponse
import com.nat.couriersapp.screens.login.domain.models.LoginRequest
import com.nat.couriersapp.screens.login.domain.models.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

interface ApiServices {
    @POST("api/Account/login")
    suspend fun userLogin(
        @Body loginRequest: LoginRequest
    ): Response<UserResponse>

    @GET("api/RunnerSheet/GetCourierSheetsTodayMobile")
    suspend fun getCouriers(
        @Query("couriorId") userId: Int,
        @Query("_date") date: String,
        @Query("type") type: String,
        @Query("clientId") clientId: String,
        @Query("keyword") keyword: String,
        @Query("status") filterQuery: String
    ): Response<HomeResponse>

    @POST("api/Courier/TrackingCourierFire")
    suspend fun sendLocation(
        @Query("courierId") userId: Int,
        @Query("latitude") date: String,
        @Query("longitude") type: String,
    ): Response<Any>

    @POST("api/Waybill/AddPodMobile")
    suspend fun deliveredCourier(
        @Body deliveredRequest : DeliveredRequest
    ): Response<DeliveredResponse>

    @PUT("api/WaybillOperation/UpdateMultipleWaybillMobile")
    suspend fun updateCourierStatus(
        @Query("LastStatusId") LastStatusId: Int,
        @Query("Comment") Comment: String,
        @Query("LastRefusalReasonId") LastRefusalReasonId: Int,
        @Query("ActionDate") ActionDate: String,
        @Query("UserId") UserId: Int,
        @Query("UserName") UserName: String,
        @Query("RoleId") RoleId: String,
        @Query("HubName") HubName: String,
        @Query("ZoneHubId") ZoneHubId: Int,
        @Query("Latitude") Latitude: String,
        @Query("Longitude") Longitude: String,
        @Body courierBody: List<CourierBody>,
    ): Response<DeliveredResponse>

    @GET("api/Status/FillCourierStatusDDLMobile")
    suspend fun statusNotDelivered(
        @Query("isActive") isActive: Boolean
    ): Response<StatusNotDeliveredResponse>

    @GET("api/RefusalReason/GetAllRefusalReasonsByStstusIdMobile")
    suspend fun reasonsByStatusNotDelivered(
        @Query("StatusId") statusId: Int
    ): Response<RefusalReasonsResponse>



}