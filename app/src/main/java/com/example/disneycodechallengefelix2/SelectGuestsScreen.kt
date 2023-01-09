package com.example.disneycodechallengefelix2

import android.widget.CheckBox
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.*
import androidx.navigation.NavController


@Composable
fun SelectGuestsScreen (
    navController: NavController,
    viewModel: SelectGuestsViewModel
){
    Column(){
        TopAppBars(navController = navController)
        SelectGuests(navController, viewModel)
    }
}

@Composable
fun TopAppBars(navController: NavController){
    TopAppBar(
        title = {
            Text(
                text = "Select Guest",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        }
    )
}

@Composable
fun SelectGuests(
    navController: NavController,
    viewModel: SelectGuestsViewModel,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {

        Spacer(modifier = Modifier.padding(10.dp))

        val uiState by viewModel.uiState.collectAsState()

        val guestNeedReservationIsChecked = remember { mutableStateOf(false)}
        val guestHaveReservationIsChecked = remember { mutableStateOf(false)}

        val guestsWithReservation = listOf(
            "Jim Jom",
            "Freddy fro",
            "Kim Kom",
            "Rim Rom",
            "Tim Tom",
            "Frim From",
            "Grim Grom",
            "Oke Coke"
        )
        val guestsNeedReservation = listOf(
            "Him Hom",
            "Lim Lom",

            )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ){
            Text(
                text = "These Guests Have Reservations", fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.padding(8.dp))

            GuestsHaveReservation(
                guestHaveReservationIsChecked,
                names = guestsWithReservation,
                viewModel
            )

            Text(
                text = "These Guests Need Reservations",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.padding(8.dp))
            GuestsNeedReservation(
                guestNeedReservationIsChecked,
                names = guestsNeedReservation,
                viewModel
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row(modifier = Modifier.padding(20.dp)) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                fontSize = 12.sp,
                text = "At least one Guest in the party must have a reservation. Guests without reservations must remain in the same booking party in order to enter"
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        val context = LocalContext.current

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(20.dp),
            onClick = {
                if (uiState.guestHaveReservation && (!uiState.guestNeedReservation)){
                    Toast.makeText(context, " To Confirmation Screen", Toast.LENGTH_SHORT).show()
                }
                else if (!uiState.guestHaveReservation && uiState.guestNeedReservation){
                    Toast.makeText(context, " Reservation Needed, Select at least one Guest that has a reservation", Toast.LENGTH_SHORT).show()
                }
                else if (uiState.guestHaveReservation && uiState.guestNeedReservation){
                    Toast.makeText(context, " Mixed party, To Conflict Screen", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue
            ),
            shape = RoundedCornerShape(40),
            enabled = (uiState.guestHaveReservation || uiState.guestNeedReservation)
        ){
            Text(
                text = "Continue",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun GuestsHaveReservation(
    isChecked:MutableState<Boolean>,
    names:List<String>,
    viewModel:SelectGuestsViewModel
){
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
){
    Column(){
        names.forEach{
            SingleGuestNeedReserve(name = it, checked = isChecked.value, viewModel)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun SingleGuestHaveReserve(
    name:String,
    checked:Boolean,
    viewModel: SelectGuestsViewModel
){
    val isChecked = remember { mutableStateOf(checked) }

    Row(
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(
            checked = isChecked.value ,
            onCheckedChange = {
                isChecked.value = it

                viewModel.countReservationGuests(it)
            },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green),
            modifier = Modifier
                .padding(5.dp)
                .size(3.dp)
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Text(
            text = name,
            modifier = Modifier.padding(start=14.dp),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Composable
fun SingleGuestNeedReserve(
    name:String,
    checked:Boolean,
    viewModel:SelectGuestsViewModel
){
    val isChecked = remember { mutableStateOf(checked) }

    Row(
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                viewModel.countNeedReservationGuests(it)
            },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green),
            modifier = Modifier
                .padding(5.dp)
                .size(3.dp)
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