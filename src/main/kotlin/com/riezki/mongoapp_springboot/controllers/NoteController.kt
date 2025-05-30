package com.riezki.mongoapp_springboot.controllers

import com.riezki.mongoapp_springboot.controllers.NoteController.NoteResponse
import com.riezki.mongoapp_springboot.controllers.model.SuccessDto
import com.riezki.mongoapp_springboot.database.model.Note
import com.riezki.mongoapp_springboot.database.repository.NoteRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@RestController
@RequestMapping("/note")
class NoteController(
    private val noteRepository: NoteRepository
) {
    data class NoteRequest(
        val id: String?,
        @field:NotBlank(message = "Title cannot be blank")
        val title: String,
        val content: String,
        val color: Long,
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createAt: Instant
    )

    @PostMapping
    fun save(
        @Valid @RequestBody body: NoteRequest
    ) : NoteResponse {
        val ownerId = SecurityContextHolder.getContext().authentication.principal.toString()
        val note = noteRepository.save(
            Note(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                color = body.color,
                ownerId = ObjectId(ownerId),
            )
        )

        return NoteResponse(
            id = note.id.toHexString(),
            title = note.title,
            content = note.content,
            color = note.color,
            createAt = note.createAt
        )
    }

    @GetMapping
    fun findByOwnerId() : List<NoteResponse> {
        val ownerId = SecurityContextHolder.getContext().authentication.principal.toString()
        return noteRepository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(
        @PathVariable id: String
    ) : SuccessDto {
        val note = noteRepository.findById(ObjectId(id)).orElseThrow {
            throw ResponseStatusException(HttpStatusCode.valueOf(404),"Note not found")
        }

        val ownerId = SecurityContextHolder.getContext().authentication.principal.toString()
        if (note.ownerId.toHexString() == ownerId) {
            noteRepository.deleteById(ObjectId(id))
            return SuccessDto("Note deleted successfully")
        }

        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found")
    }
}

private fun Note.toResponse() : NoteResponse {
    return NoteResponse(
        id = id.toHexString(),
        title = title,
        content = content,
        color = color,
        createAt = createAt
    )
}