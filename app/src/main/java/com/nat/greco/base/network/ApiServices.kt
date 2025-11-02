package com.nat.greco.base.network

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.screens.accounts.models.AccountsRequest
import com.nat.greco.screens.accounts.models.AccountsResponse
import com.nat.greco.screens.addNewOrders.models.AddToCartRequest
import com.nat.greco.screens.addNewOrders.models.AddToCartResponse
import com.nat.greco.screens.addNewOrders.models.NewProductRequest
import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.greco.screens.dayDetails.domain.models.DayDetailsResponse
import com.nat.greco.screens.dealingProducts.models.DealingProductsRequest
import com.nat.greco.screens.dealingProducts.models.DealingProductsResponse
import com.nat.greco.screens.routeDetails.domain.models.CourierBody
import com.nat.greco.screens.routeDetails.domain.models.DeliveredRequest
import com.nat.greco.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryRequest
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.greco.screens.routeDetails.domain.models.RefusalReasonsResponse
import com.nat.greco.screens.routeDetails.domain.models.StatusNotDeliveredResponse
import com.nat.greco.screens.home.domain.models.HomeResponse
import com.nat.greco.screens.home.domain.models.RouteRequest
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.models.LoginResponse
import com.nat.greco.screens.priceList.domain.models.PriceListRequest
import com.nat.greco.screens.priceList.domain.models.PriceListResponse
import com.nat.greco.screens.routeDetails.domain.models.ConfirmedAndCancelledReasonsResponse
import com.nat.greco.screens.routeDetails.domain.models.ConfirmedAndCancelledRequest
import com.nat.greco.screens.routeDetails.domain.models.TriggeredConfirmedAndCancelledResponse
import com.nat.greco.screens.stocks.models.SearchRequest
import com.nat.greco.screens.stocks.models.StockRequest
import com.nat.greco.screens.stocks.models.returnsModel.ReturnsStockResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServices {
    @POST("api/login/user")
    suspend fun userLogin(
        @Body loginRequest: BaseRequest<LoginRequest>
    ): Response<BaseResponse<LoginResponse>>

    @POST("api/get_route")
    suspend fun getRoutes(
        @Body routeRequest: BaseRequest<RouteRequest>
    ): Response<BaseResponse<HomeResponse>>


    @POST("api/get_order_history")
    suspend fun getOrderHistory(
        @Body routeRequest: BaseRequest<OrderHistoryRequest>
    ): Response<BaseResponse<List<OrderHistoryResponse>>>


    @POST("api/get_invoice_history")
    suspend fun getInvoiceHistory(
        @Body routeRequest: BaseRequest<AccountsRequest>
    ): Response<AccountsResponse>


    @POST("api/products_in_stock")
    suspend fun getProductsListHistory(
        @Body request: BaseRequest<NewProductRequest>
    ): Response<NewProductsResponse>

    @POST("api/add_to_cart")
    suspend fun addToCart(
        @Body request: BaseRequest<AddToCartRequest>
    ): Response<BaseResponse<AddToCartResponse>>


    @POST("api/products_of_dealing")
    suspend fun getDealingProducts(
        @Body request: BaseRequest<DealingProductsRequest>
    ): Response<DealingProductsResponse>


    @POST("api/stock_in_return")
    suspend fun getReturnsStock(
        @Body request: BaseRequest<StockRequest>
    ): Response<ReturnsStockResponse>

    @POST("api/customer_price_list")
    suspend fun getPriceList(
        @Body request: BaseRequest<PriceListRequest>
    ): Response<PriceListResponse>


    @GET("api/visited_reasons")
    suspend fun getConfirmedReasons(): Response<ConfirmedAndCancelledReasonsResponse>

    @GET("api/not_visited_reasons")
    suspend fun getCancelledReasons(): Response<ConfirmedAndCancelledReasonsResponse>


    @POST("api/route/visited_done")
    suspend fun confirmRoute(
        @Body request: BaseRequest<ConfirmedAndCancelledRequest>
    ): Response<TriggeredConfirmedAndCancelledResponse>

    @POST("api/route/visited_closed")
    suspend fun cancelledRoute(
        @Body request: BaseRequest<ConfirmedAndCancelledRequest>
    ): Response<TriggeredConfirmedAndCancelledResponse>

    @POST("api/day_details")
    suspend fun getDayDetails(
        @Body request: BaseRequest<DayDetailsRequest>
    ): Response<DayDetailsResponse>

    @POST("api/end_day")
    suspend fun endDay(
        @Body request: BaseRequest<DayDetailsRequest>
    ): Response<DayDetailsResponse>

    @POST("api/search_products")
    suspend fun searchWithStock(
        @Body request: BaseRequest<SearchRequest>
    ): Response<NewProductsResponse>

    @POST("api/search_products")
    suspend fun searchWithReturns(
        @Body request: BaseRequest<SearchRequest>
    ): Response<ReturnsStockResponse>










    @POST("api/Courier/TrackingCourierFire")
    suspend fun sendLocation(
        @Query("courierId") userId: Int,
        @Query("latitude") date: String,
        @Query("longitude") type: String,
    ): Response<Any>

    @POST("api/Waybill/AddPodMobile")
    suspend fun deliveredCourierWithPOD(
        @Body deliveredRequest: DeliveredRequest
    ): Response<DeliveredResponse>

    @PUT("api/WaybillOperation/UpdateMultipleWaybillMobile")
    suspend fun updateWaybillCourierStatus(
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

    @PUT("api/Pickup/UpdateMultiplePickupMobile")
    suspend fun updatePickupCourierStatus(
        @Query("LastStatusId") LastStatusId: Int,
        @Query("Comment") Comment: String,
        @Query("LastRefusalReasonId") LastRefusalReasonId: Int,
        @Query("ActionDate") ActionDate: String,
        @Query("UserId") UserId: Int,
        @Query("UserName") UserName: String,
        @Query("RoleId") RoleId: String,
        @Query("HubName") HubName: String,
        @Query("Latitude") Latitude: String,
        @Query("Longitude") Longitude: String,
        @Body courierBody: List<CourierBody>,
    ): Response<DeliveredResponse>

    @GET("api/Status/FillCourierStatusDDLMobile")
    suspend fun statusNotDelivered(
        @Query("isActive") isActive: Boolean,
        @Query("statusTypeId") statusTypeId: Int
    ): Response<StatusNotDeliveredResponse>

    @GET("api/RefusalReason/GetAllRefusalReasonsByStstusIdMobile")
    suspend fun reasonsByStatusNotDelivered(
        @Query("StatusId") statusId: Int
    ): Response<RefusalReasonsResponse>


}