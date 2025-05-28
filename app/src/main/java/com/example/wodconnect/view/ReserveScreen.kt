package com.example.wodconnect.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wodconnect.R
import com.example.wodconnect.data.model.Clases
import com.example.wodconnect.viewModel.ReserveViewModel
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReserveScreen(
    navController: NavController,
    reserveViewModel: ReserveViewModel = hiltViewModel()
) {

   Scaffold(containerColor = Color.Black,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = stringResource(R.string.text_icon_btn),
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("HomeScreen")
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.text_icon_btn),
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .focusable()
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.title_reservas),
                    modifier = Modifier.padding(start = 20.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
                Reserve(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    reserveViewModel = reserveViewModel,
                    onDaySelected = { selectedDay ->
                        reserveViewModel.obtenerClasesPorDia(selectedDay)
                    }
                )
            }

        }
    }
}

@Composable
fun BottomBar(navController: NavController) {

    Surface(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                iconRes = R.drawable.calendar,
                label = stringResource(R.string.icon_agenda),
                onClick = { navController.navigate("AgendaScreen") }
            )
            BottomBarItem(
                iconRes = R.drawable.clock,
                label = stringResource(R.string.icon_horario),
                onClick = { navController.navigate("HorarioScreen") },
            )
            BottomBarItem(
                iconRes = R.drawable.user,
                label = stringResource(R.string.icon_perfil),
                onClick = { navController.navigate("PerfilScreen") },
            )
        }
    }
}

@Composable
fun BottomBarItem(iconRes: Int, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

@Composable
fun Reserve(
    modifier: Modifier,
    reserveViewModel: ReserveViewModel,
    onDaySelected: (LocalDate) -> Unit
) {

    val daysOfWeek = reserveViewModel.daysOfWeek
    var selectedIndex by remember { mutableIntStateOf(0) }
    val selectedDate = daysOfWeek[selectedIndex].fecha

    val clasesPorDia by reserveViewModel.clasesPorDia.observeAsState()
    val isLoading by reserveViewModel.isLoading.observeAsState(false)
    val clasesReservadas by reserveViewModel.clasesReservadas.observeAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (userId != null) {
        reserveViewModel.cargarClasesYReservas(
            userId,
            selectedDate
        ) //para actualizar las reservas del usuario registrado
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black)
                    .padding(4.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),
                    verticalAlignment = Alignment.CenterVertically,
                    userScrollEnabled = true,
                ) {
                    itemsIndexed(daysOfWeek) { index, day ->
                        Box(
                            modifier = Modifier
                                .then(
                                    if (index == selectedIndex) {
                                        Modifier.clip(RoundedCornerShape(4.dp))
                                    } else {
                                        Modifier
                                    }
                                )
                                .background(
                                    if (index == selectedIndex) {
                                        colorResource(R.color.btn_color)
                                    } else {
                                        colorResource(R.color.background_card)
                                    }
                                )
                                .clickable {
                                    selectedIndex = index
                                    onDaySelected(day.fecha)
                                }
                                .padding(horizontal = 6.dp, vertical = 6.dp)

                        ) {
                            Text(
                                text = day.diaDelMes,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium
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
            if (!isLoading && clasesPorDia != null && clasesPorDia!!.isEmpty()) {
                Text(
                    text = "No hay clases para este día",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
        items(clasesPorDia ?: emptyList()) { clase ->
            val isReserved = clasesReservadas?.contains(clase.id)
            ClaseItem(
                clase = clase,
                onReservedClick = {
                    if (userId != null) {
                        reserveViewModel.reservarClase(clase, userId)
                    }
                },
                isReserved = isReserved ?: false
            )
        }
    }
}

@Composable
fun ClaseItem(clase: Clases, onReservedClick: (Clases) -> Unit, isReserved: Boolean) {

    val zoneId = ZoneId.of("Europe/Madrid")
    val start = clase.startTime?.toDate()?.toInstant()
        ?.atZone(zoneId)
        ?.toLocalTime()
        ?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""

    val end = clase.endTime?.toDate()?.toInstant()
        ?.atZone(zoneId)
        ?.toLocalTime()
        ?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""

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
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$start - $end",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = clase.description, style = MaterialTheme.typography.bodyMedium)
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

                    Text(
                        text = if (isReserved) {
                            stringResource(R.string.btn_cancelar)
                        } else {
                            stringResource(R.string.btn_reserve)
                        },
                        fontSize = 16.sp
                    )
                }
                Text(
                    text = stringResource(R.string.text_plazas),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 12.sp
                )
            }
        }

    }
}


