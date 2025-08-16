package org.notes.home.ui.comp


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun CmpDatePicker(
    initialDate: LocalDate, onDateSelected: (LocalDate) -> Unit, onDismiss: () -> Unit
) {
    var day by remember { mutableStateOf(initialDate.day) }
    var month by remember { mutableStateOf(initialDate.month) }
    var year by remember { mutableStateOf(initialDate.year) }

    AlertDialog(
        modifier = Modifier.size(width = 600.dp, height = 250.dp),
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onDateSelected(LocalDate(year, month, day))
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Select Date") },
        text = {

            Row(
                modifier = Modifier.size(width = 500.dp, height = 75.dp)
            ) {
                val currentYear =
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year

                // Day dropdown
                DropdownMenuBox(
                    label = "Day",
                    options = (1..31).map { it.toString() },
                    selected = day.toString(),
                    onSelect = { day = it.toInt() },
                    modifier = Modifier.padding(end = 4.dp).weight(1.06f).sizeIn(minWidth = 80.dp)
                )

                // Month dropdown
                DropdownMenuBox(
                    label = "Month",
                    options = (1..12).map { it.toString() },
                    selected = month.number.toString(),
                    onSelect = { month = kotlinx.datetime.Month(it.toInt()) },
                    modifier = Modifier.padding(horizontal = 2.dp).weight(1f)
                        .sizeIn(minWidth = 110.dp)
                )

                // Year dropdown
                DropdownMenuBox(
                    label = "Year",
                    options = (1900..currentYear).map { it.toString() },
                    selected = year.toString(),
                    onSelect = { year = it.toInt() },
                    modifier = Modifier.padding(start = 4.dp).weight(1.25f)
                        .sizeIn(minWidth = 120.dp)
                )
            }


        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownMenuBox(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor().sizeIn(minWidth = 90.dp) // Ensures no text wrapping
        )
        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onSelect(option)
                    expanded = false
                })
            }
        }
    }
}
