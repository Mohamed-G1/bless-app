package com.nat.bless.base.network

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.screens.accounts.models.AccountsRequest
import com.nat.bless.screens.accounts.models.AccountsResponse
import com.nat.bless.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.bless.screens.addNewClient.domain.models.AreasResponse
import com.nat.bless.screens.addNewClient.domain.models.CitiesRequest
import com.nat.bless.screens.addNewClient.domain.models.CountryResponse
import com.nat.bless.screens.addNewClient.domain.models.CustomerRequest
import com.nat.bless.screens.addNewClient.domain.models.CustomerResponse
import com.nat.bless.screens.addNewClient.domain.models.StatesRequest
import com.nat.bless.screens.addNewClient.domain.models.StatesResponse
import com.nat.bless.screens.addNewClient.domain.models.TagsResponse
import com.nat.bless.screens.addNewOrders.models.AddToCartRequest
import com.nat.bless.screens.addNewOrders.models.StockListData
import com.nat.bless.screens.addNewOrders.models.NewProductRequest
import com.nat.bless.screens.addNewOrders.models.NewProductsResponse
import com.nat.bless.screens.addNewOrders.models.addTocart.AddToCartResponse
import com.nat.bless.screens.collect.domain.models.CollectRequest
import com.nat.bless.screens.collect.domain.models.CollectResponse
import com.nat.bless.screens.collect.domain.models.CreatePaymentRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryResponse
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsRequest
import com.nat.bless.screens.dayDetails.domain.models.DayDetailsResponse
import com.nat.bless.screens.dealingProducts.models.DealingProductsRequest
import com.nat.bless.screens.dealingProducts.models.DealingProductsResponse
import com.nat.bless.screens.editableConfirmOrder.domain.models.DeleteOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.EditableConfirmOrderRequest
import com.nat.bless.screens.editableConfirmOrder.domain.models.UpdateOrderRequest
import com.nat.bless.screens.routeDetails.domain.models.CourierBody
import com.nat.bless.screens.routeDetails.domain.models.DeliveredRequest
import com.nat.bless.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryRequest
import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.bless.screens.routeDetails.domain.models.RefusalReasonsResponse
import com.nat.bless.screens.routeDetails.domain.models.StatusNotDeliveredResponse
import com.nat.bless.screens.home.domain.models.HomeResponse
import com.nat.bless.screens.home.domain.models.RouteRequest
import com.nat.bless.screens.login.domain.models.LoginRequest
import com.nat.bless.screens.login.domain.models.LoginResponse
import com.nat.bless.screens.orders.domain.models.OrderDetailsRequest
import com.nat.bless.screens.orders.domain.models.OrdersRequest
import com.nat.bless.screens.orders.domain.models.OrdersResponse
import com.nat.bless.screens.orders.domain.models.ReturnsResponse
import com.nat.bless.screens.priceList.domain.models.PriceListRequest
import com.nat.bless.screens.priceList.domain.models.PriceListResponse
import com.nat.bless.screens.promotionsList.domain.models.PromotionRequest
import com.nat.bless.screens.promotionsList.domain.models.PromotionResponse
import com.nat.bless.screens.receviceStock.domain.models.ConfirmReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockRequest
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockResponse
import com.nat.bless.screens.returnsScreen.ReturnsRequest
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledReasonsResponse
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledRequest
import com.nat.bless.screens.routeDetails.domain.models.TriggeredConfirmedAndCancelledResponse
import com.nat.bless.screens.stocks.models.SearchRequest
import com.nat.bless.screens.stocks.models.StockRequest
import com.nat.bless.screens.stocks.models.returnsModel.ReturnedListData
import com.nat.bless.screens.stocks.models.returnsModel.ReturnsStockResponse
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
    ): Response<AddToCartResponse>


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
    ): Response<BaseResponse<Any>>

    @POST("api/search_products")
    suspend fun searchWithStock(
        @Body request: BaseRequest<SearchRequest>
    ): Response<BaseResponse<List<StockListData>>>

    @POST("api/search_products")
    suspend fun searchWithReturns(
        @Body request: BaseRequest<SearchRequest>
    ): Response<BaseResponse<List<ReturnedListData>>>

    @POST("api/get_order_assigned")
    suspend fun getOrdersList(
        @Body request: BaseRequest<OrdersRequest>
    ): Response<BaseResponse<List<OrdersResponse>>>


    @POST("api/get_countries")
    suspend fun getCountriesList(
        @Body request: BaseRequest<CustomerRequest>
    ): Response<BaseResponse<List<CountryResponse>>>

    @POST("api/get_states")
    suspend fun getStatesList(
        @Body request: BaseRequest<StatesRequest>
    ): Response<BaseResponse<List<StatesResponse>>>

    @POST("api/get_cities")
    suspend fun getCitiesList(
        @Body request: BaseRequest<CitiesRequest>
    ): Response<BaseResponse<List<StatesResponse>>>


    @POST("api/get_areas")
    suspend fun getAreasList(
        @Body request: BaseRequest<CustomerRequest>
    ): Response<BaseResponse<List<AreasResponse>>>


    @POST("api/get_return_order_assigned")
    suspend fun returnsOrderList(
        @Body request: BaseRequest<OrdersRequest>
    ): Response<BaseResponse<List<ReturnsResponse>>>


    @POST("api/confirm_order")
    suspend fun confirmOrder(
        @Body request: BaseRequest<OrderDetailsRequest>
    ): Response<BaseResponse<Any>>


    @POST("api/get_order_id")
    suspend fun getOrdersDetails(
        @Body request: BaseRequest<OrderDetailsRequest>
    ): Response<BaseResponse<OrdersResponse>>


    @POST("api/get_customer_order")
    suspend fun getEditableOrdersList(
        @Body request: BaseRequest<EditableConfirmOrderRequest>
    ): Response<BaseResponse<OrdersResponse>>


    @POST("api/update_order_line")
    suspend fun updateOrdersList(
        @Body request: BaseRequest<UpdateOrderRequest>
    ): Response<BaseResponse<OrdersResponse>>


    @POST("api/delete_order_line")
    suspend fun deleteOrdersList(
        @Body request: BaseRequest<DeleteOrderRequest>
    ): Response<BaseResponse<OrdersResponse>>





    @POST("api/get_transfer_assigned")
    suspend fun getReceiveStockList(
        @Body request: BaseRequest<ReceiveStockRequest>
    ): Response<BaseResponse<List<ReceiveStockResponse>>>

    @POST("api/confirm_transfer")
    suspend fun confirmReceiveStock(
        @Body request: BaseRequest<ConfirmReceiveStockRequest>
    ): Response<BaseResponse<ReceiveStockResponse>>

    @POST("api/return_products")
    suspend fun sendReturns(
        @Body request: BaseRequest<ReturnsRequest>
    ): Response<BaseResponse<Any>>

    @POST("api/customer_promotion")
    suspend fun getPromotionList(
        @Body request: BaseRequest<PromotionRequest>
    ): Response<BaseResponse<List<PromotionResponse>>>

    @POST("api/get_customer_assigned")
    suspend fun getCustomers(
        @Body request: BaseRequest<CustomerRequest>
    ): Response<BaseResponse<List<CustomerResponse>>>

    @POST("api/create_customer")
    suspend fun addNewCustomer(
        @Body request: BaseRequest<AddCustomerRequest>
    ): Response<BaseResponse<Any>>


  @POST("api/get_journals")
    suspend fun getJournals(
        @Body request: BaseRequest<CollectRequest>
    ): Response<BaseResponse<List<CollectResponse>>>


    @POST("api/create_payment")
    suspend fun createPayment(
        @Body request: BaseRequest<CreatePaymentRequest>
    ): Response<BaseResponse<Any>>


    @POST("api/get_customer_summary")
    suspend fun getCustomerSummery(
        @Body request: BaseRequest<CustomerSummeryRequest>
    ): Response<BaseResponse<CustomerSummeryResponse>>


    @GET("api/get_tags")
    suspend fun getTags(): Response<TagsResponse>















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