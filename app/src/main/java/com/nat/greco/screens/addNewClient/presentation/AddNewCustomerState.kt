package com.nat.greco.screens.addNewClient.presentation


import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse

data class AddNewCustomerState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val error: String = "",
    val customers: List<CustomerResponse> = listOf(),

    val name: String = "",
    val isValidName: Boolean = true,
    val nameValidationMessage: String = "برجاء ادخال الاسم",


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
    val addressValidationMessage: String = "برجاء ادخال عنوان",

    )
