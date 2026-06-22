package org.jdk.project.controller

import org.jdk.project.dto.ListResponse
import org.jdk.project.dto.knowledge.KnowledgeUpsertRequest
import org.jdk.project.service.KnowledgeService
import org.jooq.generated.project.tables.pojos.KnowledgeBase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/knowledge")
class KnowledgeController(
    private val knowledgeService: KnowledgeService,
) {
    @GetMapping
    fun listKnowledge(): ListResponse<KnowledgeBase> = knowledgeService.listKnowledge()

    @GetMapping("/{id}")
    fun getKnowledge(@PathVariable id: Long): KnowledgeBase = knowledgeService.getKnowledge(id)

    @PostMapping
    fun createKnowledge(@RequestBody request: KnowledgeUpsertRequest): Long? = knowledgeService.createKnowledge(request)

    @PutMapping("/{id}")
    fun updateKnowledge(@PathVariable id: Long, @RequestBody request: KnowledgeUpsertRequest) {
        knowledgeService.updateKnowledge(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteKnowledge(@PathVariable id: Long) {
        knowledgeService.deleteKnowledge(id)
    }
}
