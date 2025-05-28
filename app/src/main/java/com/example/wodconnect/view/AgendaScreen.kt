package com.example.wodconnect.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wodconnect.viewModel.AgendaViewModel
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wodconnect.R

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
                            modifier = Modifier.clickable { navController.popBackStack() }
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
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingScaffold)
                .focusable()
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.icon_agenda),
                    modifier = Modifier.padding(start = 20.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
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
                    //se muestran las clases reservadas
                    items(clases) { clase ->
                        ClaseItem(clase = clase, onReservedClick = {}, isReserved = true)
                    }
                }
            }
        }
    }
}


