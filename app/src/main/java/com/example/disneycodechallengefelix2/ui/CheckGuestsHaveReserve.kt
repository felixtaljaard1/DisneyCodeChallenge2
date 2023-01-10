package com.example.disneycodechallengefelix2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disneycodechallengefelix2.SelectGuestsViewModel

@Composable
fun SingleGuestHaveReserve(
    name: String,
    checked: Boolean,
    viewModel: SelectGuestsViewModel
) {
    val isChecked = remember { mutableStateOf(checked) }

    Row(
        modifier = Modifier
            .semantics {
                stateDescription = if (checked){
                    "Checked" + name
                }else{
                    "Not checked" + name
                }
            }
            .toggleable(
                value = checked,
                onValueChange = {
                    isChecked.value = it

                    viewModel.countReservationGuests(it)
                }

                ,
            )
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it

                viewModel.countReservationGuests(it)
            },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green),
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = name,
            modifier = Modifier.padding(start = 14.dp),
            textAlign = TextAlign.Center,

            fontSize = 12.sp,
            color = Color.Black,

            )
    }
}

@Composable
fun SingleGuestNeedReserve(
    name: String,
    checked: Boolean,
    viewModel: SelectGuestsViewModel
) {
    val isChecked = remember { mutableStateOf(checked) }

    Row(
        modifier = Modifier
            .clearAndSetSemantics {
                stateDescription = if (checked == true){
                    "Checked"+name
                }else{
                    "Not checked"+name
                }
            }
            .toggleable(
                value = checked,
                role = Role.Checkbox,
                onValueChange = {
                    isChecked.value = it

                    viewModel.countReservationGuests(it)
                }


                ,
            )
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                viewModel.countNeedReservationGuests(it)
            },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green),
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp)
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = name,
            modifier = Modifier.padding(start = 14.dp),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Composable
fun GuestsHaveReservation(
    isChecked: MutableState<Boolean>,
    names: List<String>,
    viewModel: SelectGuestsViewModel
) {
    Column() {
        names.forEach {
            SingleGuestHaveReserve(name = it, checked = isChecked.value, viewModel)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun GuestsNeedReservation(
    isChecked: MutableState<Boolean>,
    names: List<String>,
    viewModel: SelectGuestsViewModel
) {
    Column() {
        names.forEach {
            SingleGuestNeedReserve(name = it, checked = isChecked.value, viewModel)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

