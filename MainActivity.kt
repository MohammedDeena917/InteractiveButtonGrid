// your package name here
package com.example.interactivebuttongrid

import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // ✅ Call main composable function
                InteractiveButtonGrid()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveButtonGrid() {
    // ✅ State variable for selected buttons
    var selectedButtons by remember { mutableStateOf(setOf<Int>()) }
//1. Why do we need to use remember and mutableStateOf for the selectedButtons
// variable, and what would happen if we used a normal variable instead?
    // ✅ List of button data
    val buttonData = listOf(
        ButtonData(Color(0xFFE57373), "1"),
        ButtonData(Color(0xFF81C784), "2"),
        ButtonData(Color(0xFF64B5F6), "3"),
        ButtonData(Color(0xFFFFB74D), "4"),
        ButtonData(Color(0xFFBA68C8), "5"),
        ButtonData(Color(0xFF4DB6AC), "6"),
        ButtonData(Color(0xFFFF8A65), "7"),
        ButtonData(Color(0xFF90A4AE), "8"),
        ButtonData(Color(0xFFF06292), "9"),
        ButtonData(Color(0xFF7986CB), "10"),
        ButtonData(Color(0xFF4DD0E1), "11"),
        ButtonData(Color(0xFFFFD54F), "12"),
        ButtonData(Color(0xFF8D6E63), "13"),
        ButtonData(Color(0xFF9575CD), "14"),
        ButtonData(Color(0xFF4FC3F7), "15"),
        ButtonData(Color(0xFF66BB6A), "16"),
        ButtonData(Color(0xFFFFCC02), "17"),
        ButtonData(Color(0xFFEC407A), "18"),
        ButtonData(Color(0xFF42A5F5), "19"),
        ButtonData(Color(0xFF26A69A), "20"),
        ButtonData(Color(0xFFFF7043), "21"),
        ButtonData(Color(0xFF9CCC65), "22"),
        ButtonData(Color(0xFF26C6DA), "23"),
        ButtonData(Color(0xFFD4E157), "24")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // ✅ White background
            .padding(16.dp)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ Title text
        Text(
            text = "Interactive App",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black, // ✅ Black text on white background
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ✅ Selection counter text
        Text(
            text = "Selected: ${selectedButtons.size} of ${buttonData.size}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // ✅ FlowRow grid
        //What advantages does FlowRow provide compared
        // to a simple Row or Column when arranging the buttons?
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            buttonData.forEachIndexed { index, button ->
                InteractiveButton(
                    buttonData = button,
                    isSelected = selectedButtons.contains(index),
                    onClick = {
                        selectedButtons =
                            if (selectedButtons.contains(index))
                                selectedButtons - index
                            else
                                selectedButtons + index
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ Clear selection button
        Button(
            onClick = { selectedButtons = setOf() },
            enabled = selectedButtons.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Selection")
        }
    }
}

@Composable
// Why is it a good practice to separate the InteractiveButton into its own composable
// instead of creating the button logic directly inside the FlowRow?
fun InteractiveButton(
    buttonData: ButtonData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp) // ✅ button size
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else buttonData.color,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium
            )
            //What is the difference between background() and border() modifiers,
            // and how do they work together to style the InteractiveButton?
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonData.number,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ✅ Data class
// Why is it better to group related information about a button into a data class,
// rather than keeping that information in multiple separate lists?
data class ButtonData(
    val color: Color,
    val number: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InteractiveButtonGridPreview() {
    InteractiveButtonGrid()
    // Why is it a good practice to separate the InteractiveButton into its own composable
// instead of creating the button logic directly inside the FlowRow?
}
