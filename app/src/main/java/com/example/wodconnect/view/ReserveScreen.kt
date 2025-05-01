package com.example.wodconnect.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wodconnect.R
import com.example.wodconnect.data.Class
import com.example.wodconnect.viewModel.ReserveViewModel


@Composable
fun ReserveScreen(
    navController: NavController,
    modifier: Modifier,
    viewModel: ReserveViewModel
) {
    Scaffold(
        bottomBar = { BottomBar(navController)}
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_screen))
                .padding(innerPadding)
                .focusable()
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Reserve(navController = navController, modifier = Modifier.fillMaxSize(), viewModel)
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
                Text(text = stringResource(R.string.icon_agenda),
                    style = MaterialTheme.typography.labelSmall)
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
                Text(text = stringResource(R.string.icon_horario),
                    style = MaterialTheme.typography.labelSmall)
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
                Text(text = stringResource(R.string.icon_perfil),
                    style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun Reserve(navController: NavController, modifier: Modifier, viewModel: ReserveViewModel) {
    val daysOfWeek = viewModel.daysOfWeek
    var selectedIndex by remember { mutableStateOf(0) }
    val selectedDate = daysOfWeek[selectedIndex].fecha

    //se dispara cada vez que selectedDate cambia. se llama solo cuando el día seleccionado cambia,
    // evitando múltiples llamadas innecesarias en cada recomposición.
    LaunchedEffect(selectedDate) { viewModel.setClasesForSelectedDay(selectedDate)}

    val clasesForSelectedDay by viewModel.clasesForSelectedDay.observeAsState(emptyList())

    // Log.d("Reserve", "Clases para el día seleccionado: ${clasesForSelectedDay.size}")

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
                    viewModel.reserverClass(selectedClass)
                })
            }
        }

    }

    @Composable
    fun ClaseItem(clase: Class, onReservedClick: (Class) -> Unit) {
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


    @Preview(showBackground = true)
    @Composable
    fun ReserveScreenPreview() {
        val navController = rememberNavController()
        val fakeViewModel = ReserveViewModel()
        ReserveScreen(
            navController = navController,
            modifier = Modifier,
            viewModel = fakeViewModel
        )
    }


