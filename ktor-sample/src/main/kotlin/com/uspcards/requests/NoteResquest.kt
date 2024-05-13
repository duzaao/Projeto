package com.uspcards.requests

import com.uspcards.models.Note
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
class NoteRequest(
    val titleFront: String,
    val messageFront: String,
    val titleBack: String,
    val messageBack: String
)

fun NoteRequest.toNote(
    id: UUID = UUID.randomUUID()
): Note {
    return Note(
        id = id,
        titleFront = titleFront,
        messageFront = messageFront,
        titleBack = messageBack,
        messageBack = messageBack
    )
}
