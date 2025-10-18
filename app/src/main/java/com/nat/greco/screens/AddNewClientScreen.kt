package com.nat.greco.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.MediumBlue
import com.nat.greco.ui.theme.MediumGray

@Composable
fun AddNewClientScreen(
    onBackClicked: (() -> Unit)? = null,
) {
    var isSingleSelected by remember { mutableStateOf(false) }
    var isMultiSelected by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "عميل جديد",
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
                "البيانات الرئيسية",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))

            ClientName()

            Spacer(Modifier.height(8.dp))
            ChooseLocation()
            Spacer(Modifier.height(8.dp))
            Tax()
            Spacer(Modifier.height(8.dp))
            AttachTax()
            Spacer(Modifier.height(8.dp))
            Department()

            Spacer(Modifier.height(8.dp))
            Tags()

            Spacer(Modifier.height(32.dp))

            Text(
                "بيانات الاتصال",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))
            AddName()
            Spacer(Modifier.height(8.dp))

            Phone()
            Spacer(Modifier.height(8.dp))

            Email()
            Spacer(Modifier.height(8.dp))

            Title()

            Spacer(Modifier.height(32.dp))

            Text(
                "اجمالي المبيعات",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))
            TotalSeals()



            Spacer(Modifier.height(32.dp))

            Text(
                "اختر اذا كان لديك فرع واحد او اكثر",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(16.dp))

            ChooseBranches(
                inSingleSelected = isSingleSelected,
                isMutliSelected = isMultiSelected,
                onSingleSelected = {
                    isSingleSelected = true
                    isMultiSelected = false
                },
                onMutliSelected = {
                    isSingleSelected = false
                    isMultiSelected = true
                }
            )



            Spacer(Modifier.height(28.dp))
            AppButton(
                text = "اضافة",
                onClick = {
                }
            )

        }
    }
}


@Composable
private fun TotalSeals() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل اجمالي المبيعات",
        label = "ادخل اجمالي المبيعات",
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
fun ChooseBranches(
    inSingleSelected: Boolean,
    isMutliSelected: Boolean,
    onSingleSelected: () -> Unit,
    onMutliSelected: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                "فرع واحد",
                style = CompactTypography.headlineMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            RadioButton(
                selected = inSingleSelected,
                onClick = { onSingleSelected.invoke() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MediumBlue
                )
            )
        }
        Column {
            Text(
                "اكثر من فرع",
                style = CompactTypography.headlineMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            RadioButton(
                selected = isMutliSelected,
                onClick = { onMutliSelected.invoke() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MediumBlue
                )
            )
        }

    }
}

@Composable
private fun ClientName() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل اسم العميل",
        label = "ادخل اسم العميل",
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
private fun Tax() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل الرقم الضريبي",
        label = "ادخل الرقم الضريبي",
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
private fun AttachTax() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "إلحاق صورة الرقم الضريبي",
        label = "إلحاق صورة الرقم الضريبي",
        onValueChange = {
//            event?.invoke(LoginEvents.UserNameChanged(it))
        },

        enabled = false,
        readOnly = true,
        isTrailingIcon = true,
        trailingCompose = { Icon(imageVector = Icons.Default.AddCircle, contentDescription = "", tint = MediumGray)},
        borderUnFocusColor = MediumGray,
        borderFocusColor = MediumGray
    )
}

@Composable
private fun Department() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "القسم",
        label = "القسم",
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
private fun Tags() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "العلامات",
        label = "العلامات",
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
private fun AddName() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل الاسم",
        label = "ادخل الاسم",
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
private fun Phone() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل هاتف",
        label = "ادخل هاتف",
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
private fun Email() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل بريد الكتروني",
        label = "ادخل بريد الكتروني",
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
private fun Title() {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل اللقب",
        label = "ادخل اللقب",
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

@Preview(showBackground = true)
@Composable
private fun AddNewClientScreenPreview() {
    AddNewClientScreen()
}

@Preview(showBackground = true)
@Composable
private fun ChooseBra() {
    ChooseBranches(false, false, {}) { }
}