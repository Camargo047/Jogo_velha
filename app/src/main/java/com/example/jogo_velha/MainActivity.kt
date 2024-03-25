package com.example.jogo_velha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    val winningPositions = listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    fun checkWinner(): String {
        for (position in winningPositions) {
            val (a, b, c) = position
            if (gameState[a].isNotEmpty() && gameState[a] == gameState[b] && gameState[a] == gameState[c]) {
                gameActive = false
                return gameState[a]
            }
        }
        if (!gameState.contains("")) {
            gameActive = false
            return "EMPATE"
        }
        return ""
    }

    val winner = checkWinner()

    if (winner.isNotEmpty()) {
        val message = when (winner) {
            "EMPATE" -> "O jogo terminou em empate!"
            else -> "O jogador $winner venceu!"
        }
        AlertDialog(
            onDismissRequest = { /* Não faz nada, mantém o diálogo aberto */ },
            title = { Text(text = "Fim de jogo") },
            text = { Text(text = message) },
            confirmButton = {
                Button(
                    onClick = {
                        gameState = List(9) { "" }
                        currentPlayer = "X"
                        gameActive = true
                    }
                ) {
                    Text(text = "Novo jogo")
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Jogo da Velha",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(25.dp))

        Grid(
            gridState = gameState,
            onCellClicked = { index ->
                if (gameActive && gameState[index].isEmpty()) {
                    gameState = gameState.toMutableList().apply {
                        this[index] = currentPlayer
                    }
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                }
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                gameState = List(9) { "" }
                currentPlayer = "X"
                gameActive = true
            }
        ) {
            Text(text = "LIMPAR TELA", fontSize = 30.sp)
        }
    }
}

@Composable
fun Grid(gridState: List<String>, onCellClicked: (Int) -> Unit) {
    Column {
        for (i in 0 until 3) {
            Row {
                for (j in 0 until 3) {
                    val index = i * 3 + j
                    Cell(
                        modifier = Modifier
                            .size(100.dp)
                            .clickable { onCellClicked(index) },
                        text = gridState[index]
                    )
                }
            }
        }
    }
}

@Composable
fun Cell(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 24.sp)
    }
}

@Preview
@Composable
fun PreviewTicTacToeGame() {
    TicTacToeGame()
}
