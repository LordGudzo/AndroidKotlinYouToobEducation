package com.firstproject.notes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp() {
    var notes by remember { mutableStateOf(listOf<OneNote>()) }
    var isShowDialogAlert by remember { mutableStateOf(false) }
    var titleNote by remember { mutableStateOf("") }
    var descriptionNote by remember { mutableStateOf("") }

    Column {
        LazyColumn(
            modifier = Modifier.padding(12.dp)
        ) {
            items(notes) {

            }
        }

        //<editor-fold desc="Add btn">
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    isShowDialogAlert = true
                    titleNote = ""
                    descriptionNote = ""
                }
            ) { Text("+") }
        }
        //</editor-fold>

        if(isShowDialogAlert){
            AlertDialog(
                title = {Text("Add new note")},
                onDismissRequest = {isShowDialogAlert = false},
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if(titleNote.isNotBlank()){
                                    isShowDialogAlert = false
                                    val newNote = OneNote(notes.size + 1, titleNote, descriptionNote)
                                    notes = notes + newNote

                                    Log.d("TEST", "NotesApp: $notes")
                                }
                            }
                        ) {Text("Ok") }

                        Button(
                            onClick = {
                                isShowDialogAlert = false
                            }
                        ) {Text("Cancel") }
                    }
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = titleNote,
                            onValueChange = {titleNote = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            singleLine = true,
                        )

                        OutlinedTextField(
                            value = descriptionNote,
                            onValueChange = {descriptionNote = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            minLines = 5,
                            maxLines = 9,
                        )
                    }
                }
            )
        }
    }
}

data class OneNote(
    val id: Int,
    var title: String,
    var description: String,
    var isEdition: Boolean = false,
)