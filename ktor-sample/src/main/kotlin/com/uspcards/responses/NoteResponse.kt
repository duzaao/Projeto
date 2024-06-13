package com.uspcards.responses

import kotlinx.serialization.Serializable

@Serializable
class NoteResponse(
    val id: String,
    val titleFront: String,
    val messageFront: String,
    val titleBack: String,
    val messageBack: String,
    val priority : Int
)
