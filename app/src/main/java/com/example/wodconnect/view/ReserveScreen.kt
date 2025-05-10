package com.example.wodconnect.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wodconnect.R
import com.example.wodconnect.data.Clases
import com.example.wodconnect.modelo.Workout
import com.example.wodconnect.viewModel.ReserveViewModel
import com.example.wodconnect.viewModel.WorkoutViewModel


@Composable
fun ReserveScreen(
    navController: NavController,
    modifier: Modifier,
    reserveViewModel: ReserveViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_screen))
                .padding(innerPadding)
                .focusable()
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Reserve(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    reserveViewModel
                )
            }

        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    Surface(
        tonalElevation = 4.dp,
        color = colorResource(R.color.background_screen),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("agenda") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.calendar),
                    contentDescription = stringResource(R.string.icon_agenda),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.icon_agenda),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            IconButton(
                onClick = { navController.navigate("horarios") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.clock),
                    contentDescription = stringResource(R.string.icon_horario),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.icon_horario),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            IconButton(
                onClick = { navController.navigate("perfil") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.user),
                    contentDescription = stringResource(R.string.icon_perfil),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.icon_perfil),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun Reserve(navController: NavController, modifier: Modifier, reserveViewModel: ReserveViewModel) {
    val daysOfWeek = reserveViewModel.daysOfWeek
    var selectedIndex by remember { mutableStateOf(0) }
    val selectedDate = daysOfWeek[selectedIndex].fecha

    val selectedClass by remember { mutableStateOf<Workout?>(null) }

    //se dispara cada vez que selectedDate cambia. Se llama solo cuando el día seleccionado cambia,
    // evitando múltiples llamadas innecesarias en cada recomposición.
    LaunchedEffect(selectedDate) { reserveViewModel.setClasesForSelectedDay(selectedDate) }

    val clasesForSelectedDay by reserveViewModel.clasesForSelectedDay.observeAsState(emptyList())


    var showDialog by remember { mutableStateOf(false) }
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val selectedWorkout by workoutViewModel.selectedWorkout.observeAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(R.color.background_screen))
                    .padding(4.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    userScrollEnabled = true,

                    ) {
                    itemsIndexed(daysOfWeek) { index, day ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (index == selectedIndex) {
                                        colorResource(R.color.btn_color)
                                    } else {
                                        colorResource(R.color.item_fecha)
                                    }
                                )
                                .clickable { selectedIndex = index }
                                .padding(horizontal = 6.dp, vertical = 6.dp)

                        ) {
                            Text(
                                text = day.diaDelMes,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                    }
                }

            }
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            if (clasesForSelectedDay.isEmpty()) {
                Text(
                    text = "No hay clases para este día",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        items(clasesForSelectedDay) { clase ->
            ClaseItem(clase = clase, onReservedClick = { selectedClass ->
                reserveViewModel.reserverClass(selectedClass)
            })
        }
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = { //se busca en la Api las clases correspondientes a crossfit para mostrarlas
            val crossfitClass = clasesForSelectedDay.firstOrNull { it.name == "Crossfit" }
            crossfitClass?.let {
                workoutViewModel.obtenerWorkoutsById(it.id.toString())

                showDialog = true
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.btn_color),
            disabledContainerColor = colorResource(R.color.btn_disable_color),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),

        ) {
        Text(
            text = stringResource(R.string.btn_workout),
            fontSize = 20.sp

        )
    }

    if (showDialog && selectedWorkout != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = selectedWorkout!!.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            },

            text = {
                Column {
                    Text("Modd: ${selectedWorkout!!.mode}")
                    Text("Ejercicios:\n${selectedWorkout!!.exercises.joinToString("\n")}")
                    Text("Equipamiento:\n${selectedWorkout!!.equipment.joinToString("\n")}")
                    Text("Tips:\n${selectedWorkout!!.trainerTips.joinToString("\n")}")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White,
                        containerColor = colorResource(id = R.color.btn_color)
                    )
                ) {
                    Text(text = stringResource(R.string.btn_dialog))
                }
            }
        )
    }
}
@Composable
fun ClaseItem(clase: Clases, onReservedClick: (Clases) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.background_card))
    ) {
        Row(modifier = Modifier.fillMaxWidth())
        {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
            {
                Text(
                    text = clase.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${clase.startTime} - ${clase.endTime}",
                    style = MaterialTheme.typography.bodySmall
                )
                clase.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodySmall)
                }
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.End)
            )
            {
                TextButton(
                    onClick = { onReservedClick(clase) },
                    modifier = Modifier
                        .width(150.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White,
                        containerColor = colorResource(R.color.btn_color)
                    )
                ) {
                    Text(text = stringResource(R.string.btn_reserve))
                }
                Text(
                    text = stringResource(R.string.text_plazas),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 12.sp
                )
            }
        }

    }
}


