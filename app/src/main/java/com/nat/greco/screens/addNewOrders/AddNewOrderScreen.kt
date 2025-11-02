package com.nat.greco.screens.addNewOrders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.MediumGray

@Composable
fun AddNewOrderScreen(
    onBackClicked: (() -> Unit)? = null,
    navigateToProducts: (() -> Unit)? = null,

    ) {
    Box(
        modifier = Modifier
            .fillMaxSize() .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)

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

            ChooseClient()
            Spacer(Modifier.height(8.dp))
            ChooseLocation()
            Spacer(Modifier.height(8.dp))
            ChoosePrice()
            Spacer(Modifier.height(8.dp))
            ChoosePriceValue()
            Spacer(Modifier.height(8.dp))
            ChooseDate()





        }

        AppButton(
            text = "اضافة منتج",
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                navigateToProducts?.invoke()
            }
        )
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
    AddNewOrderScreen()
}