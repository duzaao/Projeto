package com.uspcards.modules

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import com.uspcards.models.toNoteResponse
import com.uspcards.requests.NoteRequest
import com.uspcards.requests.toNote
import com.uspcards.services.NoteService

import java.util.*

fun Application.configureNoteRouting(
    service: NoteService
) {
    routing {
        route("/users/{userId}/notes") {
            get {
                val userId = call.parameters["userId"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid User ID")
                val notes = service.findAllOrderedByPriority(userId)
                call.respond(HttpStatusCode.OK, notes.map { it.toNoteResponse() })
            }
            get("/{id}") {
                val userId = call.parameters["userId"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid User ID")
                val noteId = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid Note ID")
                val note = service.findById(userId, noteId)
                if (note != null) {
                    call.respond(HttpStatusCode.OK, note.toNoteResponse())
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            post {
                val userId = call.parameters["userId"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid User ID")
                val noteRequest = call.receive<NoteRequest>()
                val note = noteRequest.toNote()
                val response = service.save(userId, note).toNoteResponse()
                call.respond(HttpStatusCode.Created, response)
            }
            put("/{noteId}") {
                val userId = call.parameters["userId"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid User ID")
                val noteId = call.parameters["noteId"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid Note ID")
                val noteRequest = call.receive<NoteRequest>()
                val note = noteRequest.toNote(noteId)
                val response = service.save(userId, note).toNoteResponse()
                call.respond(HttpStatusCode.OK, response)
            }

            delete("/{noteId}") {
                val userId = call.parameters["userId"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid User ID")
                val noteId = call.parameters["noteId"]?.let { UUID.fromString(it) }
                    ?: throw IllegalArgumentException("Invalid Note ID")
                service.delete(userId, noteId)
                call.respond(HttpStatusCode.OK)
            }
        }

    }
}
