package com.example.wodconnect.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wodconnect.R
import com.example.wodconnect.data.model.toLocalDate
import com.example.wodconnect.viewModel.AgendaViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(
    agendaViewModel: AgendaViewModel = hiltViewModel(),
    navController: NavController
) {
    val clases by agendaViewModel.clasesReservadas.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        agendaViewModel.cargarReservasUsuario()
    }

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
                            modifier = Modifier.clickable { navController.navigate("ReserveScreen")  }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("ReserveScreen")
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.text_icon_btn),
                            tint = Color.White
                        )
                    }
                }
            )
        }

    ) { paddingScaffold ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingScaffold)
        ) {
            Text(
                text = stringResource(R.string.icon_agenda),
                modifier = Modifier.padding(start = 20.dp),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            //se ordenan las clases por fecha y hora
            val clasesPorFechayHora = clases
                .filter { it.startTime != null }
                .groupBy { it.startTime!!.toLocalDate() }
                .toSortedMap()

            LazyColumn(
                modifier = Modifier
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
                    )

                }
                clasesPorFechayHora.forEach { (fecha, clasesDelDia) ->
                    val fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale("es")))

                    item{
                        Text(
                            text = fechaFormateada.replaceFirstChar { it.uppercaseChar() },
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }
                //se muestran las clases reservadas
                items(clasesDelDia.sortedBy { it.startTime }) { clase ->
                        ClaseItem(clase = clase, onReservedClick = {}, isReserved = true)
                    }
                }
            }
        }
        if (clases.isEmpty()) {
            Text(
                text = "No hay clases reservadas",
                color = Color.White,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}



