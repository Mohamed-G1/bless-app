package com.nat.greco.screens.addNewOrders.presentation.chooseCustomer

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.DarkGray
import com.nat.greco.ui.theme.MediumGray
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun ChooseCustomerScreen(
    state: ChooseCustomerState,
    onBackClicked: (() -> Unit)? = null,
    navigateToProducts: ((Int) -> Unit)? = null
) {

    var customerId by remember { mutableIntStateOf(0) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "طلب جديد",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                "بيانات العميل",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))

            ChooseClientDropDownMenu(
                customers = state.customers,
                onCustomerSelected = { id ->
                    customerId = id
                }
            )
//            Spacer(Modifier.height(8.dp))
//            ChooseLocation()
//            Spacer(Modifier.height(8.dp))
//            ChoosePrice()
//            Spacer(Modifier.height(8.dp))
//            ChoosePriceValue()
//            Spacer(Modifier.height(8.dp))
//            ChooseDate()


        }

        AppButton(
            text = "اضافة منتج",
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                if (customerId != 0)
                    navigateToProducts?.invoke(customerId)
                else
                    Toast.makeText(context, "برجاء اختيار عميل", Toast.LENGTH_SHORT).show()

            }
        )
    }
    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.isLoading) {
        FullLoading()
    }
}


@Composable
private fun ChooseClientDropDownMenu(
    customers: List<CustomerResponse>,
    onCustomerSelected: ((Int) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }
    var selectedId by remember { mutableIntStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }


    Card(
        onClick = {
            expanded = !expanded
        },
        border = BorderStroke(1.dp, WhiteGray),
        colors = CardDefaults.cardColors(containerColor = WhiteGray),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    fieldSize = coordinates.size.toSize()

                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedName.ifBlank { "اختر عميل" },
                style = CompactTypography.labelMedium.copy(
                    color = if (selectedName.isBlank()) Color.Gray else Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                modifier = Modifier
                    .background(White)
                    .padding(2.dp)
                    .width(with(LocalDensity.current) { fieldSize.width.toDp() })
            ) {
                customers.forEach { customer ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = customer.name,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                style = CompactTypography.labelMedium.copy(
                                    color = Black,
                                    textAlign = TextAlign.End,
                                    fontSize = 12.sp
                                )
                            )
                        },
                        onClick = {
                            selectedName = customer.name
                            selectedId = customer.id
                            expanded = false
                            onCustomerSelected?.invoke(customer.id)
                        }
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = DarkGray
            )

        }

    }
}

@Composable
private fun ChooseClient(

) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "اختر عميل",
        label = "اختر عميل",
        onValueChange = {
//            event?.invoke(LoginEvents.UserNameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray,


        )
}

@Composable
private fun ChooseLocation() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل عنوان",
        label = "ادخل عنوان",
        onValueChange = {
//            event?.invoke(LoginEvents.UserNameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun ChoosePrice() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "قائمة الاسعار",
        label = "قائمة الاسعار",
        onValueChange = {
//            event?.invoke(LoginEvents.UserNameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun ChoosePriceValue() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "المدفوعات",
        label = "المدفوعات",
        onValueChange = {
//            event?.invoke(LoginEvents.UserNameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun ChooseDate() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "اختر تاريخ",
        label = "اختر تاريخ",
        onValueChange = {
//            event?.invoke(LoginEvents.UserNameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}
//@Composable
//private fun AddProduce() {
//    AppTextField(
//        modifier = Modifier.fillMaxWidth(),
//        placeholder = "اضف منتج",
//        label = "اضف منتج",
//        onValueChange = {
////            event?.invoke(LoginEvents.UserNameChanged(it))
//        },
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Text,
//            imeAction = ImeAction.Next
//        ),
//        borderUnFocusColor = MediumGray
//    )
//}

@Preview(showBackground = true)
@Composable
private fun NewReguestSceenPreview() {
    ChooseCustomerScreen(ChooseCustomerState())
}