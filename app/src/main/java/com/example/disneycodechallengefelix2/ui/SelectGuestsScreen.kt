package com.example.disneycodechallengefelix2

import android.widget.CheckBox
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.*
import androidx.navigation.NavController
import com.example.disneycodechallengefelix2.ui.*


@Composable
fun SelectGuestsScreen(
    navController: NavController,
    viewModel: SelectGuestsViewModel
) {
    Column() {
        TopAppBars(navController = navController)
        SelectGuests(navController, viewModel)
    }
}


@Composable
fun SelectGuests(
    navController: NavController,
    viewModel: SelectGuestsViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {

        Spacer(modifier = Modifier.padding(10.dp))

        val uiState by viewModel.uiState.collectAsState()

        val guestNeedReservationIsChecked = remember { mutableStateOf(false) }
        val guestHaveReservationIsChecked = remember { mutableStateOf(false) }

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
        ) {
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
                    .clearAndSetSemantics { }
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
                if (uiState.guestHaveReservation && (!uiState.guestNeedReservation)) {
                    Toast.makeText(context, " To Confirmation Screen", Toast.LENGTH_SHORT).show()
                } else if (!uiState.guestHaveReservation && uiState.guestNeedReservation) {
                    Toast.makeText(
                        context,
                        " Reservation Needed, Select at least one Guest that has a reservation",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (uiState.guestHaveReservation && uiState.guestNeedReservation) {
                    Toast.makeText(context, " Mixed party, To Conflict Screen", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue
            ),
            shape = RoundedCornerShape(40),
            enabled = (uiState.guestHaveReservation || uiState.guestNeedReservation)
        ) {
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

