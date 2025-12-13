package com.nat.greco.screens.addNewClient.presentation


import com.nat.greco.screens.addNewClient.domain.models.AreasResponse
import com.nat.greco.screens.addNewClient.domain.models.CountryResponse
import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse
import com.nat.greco.screens.addNewClient.domain.models.StatesResponse

data class AddNewCustomerState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val error: String = "",
    val customers: List<CustomerResponse> = listOf(),
    val countries: List<CountryResponse> = listOf(),
    val states: List<StatesResponse> = listOf(),
    val cities: List<StatesResponse> = listOf(),
    val areas: List<AreasResponse> = listOf(),

    val countryId : Int = 0,
    val isValidCountry: Boolean = true,
    val countryValidationMessage: String = "برجاء اختار محافظة",


    val statesId : Int = 0,
    val isValidState: Boolean = true,
    val stateValidationMessage: String = "برجاء اختار المنطقة",

    val cityId : Int = 0,
    val isValidCity: Boolean = true,
    val cityValidationMessage: String = "برجاء اختار مدينة",

    val area : Int = 0,
    val isValidArea: Boolean = true,
    val areaValidationMessage: String = "برجاء اختار منطقة",

    val name: String = "",
    val isValidName: Boolean = true,
    val nameValidationMessage: String = "برجاء ادخال الاسم",


    val location: String = "",
    val lat: String = "",
    val long: String = "",
    val isValidLocation: Boolean = true,
    val locationValidationMessage: String = "برجاء اختار الموقع",




    val phone: String = "",
    val isValidPhone: Boolean = true,
    val phoneValidationMessage: String = "برجاء ادخال رقم الموبايل",

    val email: String = "",
    val isValidEmail: Boolean = true,
    val emailValidationMessage: String = "برجاء ادخال بريد الكتروني",

    val contract: String = "",
    val isValidContract: Boolean = true,
    val contractValidationMessage: String = "برجاء ادخال تفاصيل",

    val address: String = "",
    val isValidAddress: Boolean = true,
    val addressValidationMessage: String = "برجاء ادخال علامة مميزة",

    )
