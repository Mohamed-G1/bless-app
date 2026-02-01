package com.nat.bless.screens.editableConfirmOrder

sealed class EditableConfirmOrderEvents {

    data class CustomerIdChanged(val customerId: Int) : EditableConfirmOrderEvents()
    data class UpdateItem(val counter: Int, val lineId : Int, val price : Double) : EditableConfirmOrderEvents()
    data class DeleteItem(val lineId : Int) : EditableConfirmOrderEvents()

}