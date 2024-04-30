package com.uspcards.models

import com.uspcards.responses.NoteResponse
import java.util.*

class Note(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val message: String
)

fun Note.toNoteResponse(): NoteResponse {
    return NoteResponse(
        id = id.toString(),
        title = title,
        message = message
    )
}