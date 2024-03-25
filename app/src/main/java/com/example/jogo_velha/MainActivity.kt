package com.example.jogodavelha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGame()
        }
    }
}

@Composable
fun TicTacToeGame() {
    var gameState by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var gameActive by remember { mutableStateOf(true) }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Spacer(
            modifier = Modifier
                .height(16.dp))

        // Display the game grid
        Grid(
            gridState = gameState,
            onCellClicked = { index ->
                if (gameActive && gameState[index] == "") {
                    gameState = gameState.toMutableList().apply {
                        this[index] = currentPlayer
                    }
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    // Check for win or draw
                }
            })

        Spacer(
            modifier = Modifier
                .height(16.dp))

        Button(
            onClick = {
                gameState = List(9) { "" }
                currentPlayer = "X"
                gameActive = true
            }) {
            Text(text = "LIMPAR TELA", fontSize = 30.sp)
        }
    }
}

@Composable
fun Grid(gridState: List<String>, onCellClicked: (Int) -> Unit) {
    BoxWithConstraints {
        val size = minWidth / 3
        Column {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        val index = i * 3 + j
                        Box(modifier = Modifier
                            .size(size)
                            .background(Color.Yellow)
                            .clickable { onCellClicked(index) },
                            contentAlignment = Alignment.Center) {
                            Text(text = gridState[index], fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTicTacToeGame() {
    TicTacToeGame()
}

@Preview
@Composable
fun PreviewGrid() {
    Grid(gridState = listOf("X", "", "O", "", "X", "", "", "", "O")) {}
}
