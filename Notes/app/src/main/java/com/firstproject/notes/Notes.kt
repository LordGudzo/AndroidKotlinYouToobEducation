package com.firstproject.notes

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
            items(notes) { note ->

                if (note.isEdition) {

                    NoteEditView(
                        item = note,
                        onEditComplete = { editName,
                                           editDescription ->
                            notes = notes.map { it.copy(isEdition = false) }
                            val editNote = notes.find { it.id == note.id }
                            editNote?.let {
                                it.title = editName
                                it.description = editDescription
                            }
                        }
                    )

                } else {
                    NoteView(
                        item = note,
                        onEditClick = {
                            notes = notes.map {
                                it.copy(isEdition = (it.id == note.id))
                            }
                        },
                        onDeleteClick = {
                            notes = notes - note
                        }
                    )
                }

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

        //<editor-fold desc="AlertDialog">
        if (isShowDialogAlert) {
            AlertDialog(
                title = { Text("Add new note") },
                onDismissRequest = { isShowDialogAlert = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (titleNote.isNotBlank()) {
                                    isShowDialogAlert = false
                                    val newNote =
                                        OneNote(notes.size + 1, titleNote, descriptionNote)
                                    notes = notes + newNote

                                    Log.d("TEST", "NotesApp: $notes")
                                }
                            }
                        ) { Text("Ok") }

                        Button(
                            onClick = {
                                isShowDialogAlert = false
                            }
                        ) { Text("Cancel") }
                    }
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = titleNote,
                            onValueChange = { titleNote = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            singleLine = true,
                        )

                        OutlinedTextField(
                            value = descriptionNote,
                            onValueChange = { descriptionNote = it },
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
        //</editor-fold>
    }
}

@Composable
fun NoteView(item: OneNote, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                border = BorderStroke(2.dp, Color.DarkGray),
                shape = RoundedCornerShape(15)
            )

    ) {
        //<editor-fold desc="Title field">
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Row {
                Text(
                    item.title,
                    maxLines = 2,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                )
            }
            Row {
                IconButton(onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                    )
                }
                IconButton(onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                    )
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Divider">
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        //</editor-fold>

        //<editor-fold desc="Description field">
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(item.description, maxLines = 4)
        }
        //</editor-fold>
    }
}

@Composable
fun NoteEditView(item: OneNote, onEditComplete: (String, String) -> Unit) {
    var editName by remember { mutableStateOf(item.title) }
    var editDescription by remember { mutableStateOf(item.description) }
    var isEditing by remember { mutableStateOf(item.isEdition) }

    Column {
        Row() {
            Column {
                BasicTextField(
                    value = editName,
                    onValueChange = { editName = it },
                    singleLine = true,
                    modifier = Modifier.padding(8.dp)
                )
                BasicTextField(
                    value = editDescription,
                    onValueChange = { editDescription = it },
                    maxLines = 10,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Row() {
            Button(onClick = {
                isEditing = false
                onEditComplete(editName, editDescription)
            }) {
                Text(text = "Ok")
            }
        }
    }

}

data class OneNote(
    val id: Int,
    var title: String,
    var description: String,
    var isEdition: Boolean = false,
)