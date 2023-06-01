package id.ac.unpas.tubes.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import id.ac.unpas.tubes.ui.theme.Purple700
import id.ac.unpas.tubes.ui.theme.Teal200
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun FormPencatatanMobilScreen(
    navController: NavHostController, id: String? = null, modifier: Modifier = Modifier
) {
    val isLoading = remember { mutableStateOf(false) }
    val buttonLabel = if (isLoading.value) "Mohon tunggu..." else "Simpan"
    val viewModel = hiltViewModel<PengelolaanMobilViewModel>()
    val merk = remember { mutableStateOf(TextFieldValue("")) }
    val model = remember { mutableStateOf(TextFieldValue("")) }
    val bahanBakarOptions = listOf("Bensin", "Solar", "Listrik")
    val bahanBakar = remember { mutableStateOf("") }
    val dijual = remember { mutableStateOf(TextFieldValue("")) }
    val deskripsi = remember { mutableStateOf(TextFieldValue("")) }
//    val tanggalDialogState = rememberMaterialDialogState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(label = { Text(text = "Merk") }, value = merk.value, onValueChange = {
            merk.value = it
        }, modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text
        ), placeholder = { Text(text = "XXXXX") })

        OutlinedTextField(label = { Text(text = "Model") }, value = model.value, onValueChange = {
            model.value = it
        }, modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text
        ), placeholder = { Text(text = "XXXXX") })

        Text(
            text = "Bahan Bakar",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bahanBakarOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .selectable(
                            selected = (bahanBakar.value == option),
                            onClick = {
                                bahanBakar.value = option
                            }
                        )
                        .padding(4.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    RadioButton(
                        selected = (bahanBakar.value == option),
                        onClick = {
                            bahanBakar.value = option
                        }
                    )
                    Text(
                        text = option.toString(),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        OutlinedTextField(label = { Text(text = "Dijual") }, value = dijual.value, onValueChange = {
            dijual.value = it
        }, modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text
        ), placeholder = { Text(text = "XXXXX") })

        OutlinedTextField(label = { Text(text = "Deskripsi") }, value = deskripsi.value, onValueChange = {
            deskripsi.value = it
        }, modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text
        ), placeholder = { Text(text = "XXXXX") })

        val loginButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Purple700, contentColor = Teal200
        )
        val resetButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Teal200, contentColor = Purple700
        )

        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Button(modifier = Modifier.weight(5f), onClick = {
                if (id == null) {
                    scope.launch {
                        viewModel.insert(
                            merk.value.text, model.value.text, bahanBakar.value, dijual.value.text, deskripsi.value.text
                        )
                    }
                } else {
                    scope.launch {
                        viewModel.update(
                            id, merk.value.text, model.value.text, bahanBakar.value, dijual.value.text, deskripsi.value.text
                        )
                    }
                }
                navController.navigate("pengelolaan-mobil")
            }, colors = loginButtonColors) {
                Text(
                    text = buttonLabel, style = TextStyle(
                        color = Color.White, fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
            Button(modifier = Modifier.weight(5f), onClick = {
                model.value = TextFieldValue("")
                model.value = TextFieldValue("")
                bahanBakar.value = ""
                dijual.value = TextFieldValue("")
                deskripsi.value = TextFieldValue("")
            }, colors = resetButtonColors) {
                Text(
                    text = "Reset", style = TextStyle(
                        color = Color.White, fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
    viewModel.isLoading.observe(LocalLifecycleOwner.current) {
        isLoading.value = it
    }

    if (id != null) {
        LaunchedEffect(scope) {
            viewModel.loadItem(id) { mobil ->
                mobil?.let {
                    merk.value = TextFieldValue(mobil.merk)
                    model.value = TextFieldValue(mobil.model)
                    bahanBakar.value = ""
                    dijual.value = TextFieldValue(mobil.dijual)
                    deskripsi.value = TextFieldValue(mobil.deskripsi)
                }
            }
        }
    }

//    MaterialDialog(dialogState = tanggalDialogState, buttons = {
//        positiveButton("OK")
//        negativeButton("Batal")
//    }) {
//        datepicker { date ->
//            tanggalAwal.value =
//                TextFieldValue(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
//        }
//    }
}