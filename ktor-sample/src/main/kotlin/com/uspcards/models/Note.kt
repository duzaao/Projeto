package com.uspcards.models

import com.uspcards.responses.NoteResponse
import java.util.*

class Note(
    val id: UUID = UUID.randomUUID(),
    val titleFront: String,
    val messageFront: String,
    val titleBack: String,
    val messageBack: String,
    val priority : Int
)

fun Note.toNoteResponse(): NoteResponse {
    return NoteResponse(
        id = id.toString(),
        titleFront = titleFront,
        messageFront = messageFront,
        titleBack = titleBack,
        messageBack = messageBack,
        priority = priority
    )
}
