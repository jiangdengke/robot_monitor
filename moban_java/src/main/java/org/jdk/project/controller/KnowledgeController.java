package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.knowledge.KnowledgeUpsertRequest;
import org.jooq.generated.project.tables.pojos.KnowledgeBase;
import org.jdk.project.service.KnowledgeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

  private final KnowledgeService knowledgeService;

  @GetMapping
  public ListResponse<KnowledgeBase> listKnowledge() {
    return knowledgeService.listKnowledge();
  }

  @GetMapping("/{id}")
  public KnowledgeBase getKnowledge(@PathVariable Long id) {
    return knowledgeService.getKnowledge(id);
  }

  @PostMapping
  public Long createKnowledge(@RequestBody KnowledgeUpsertRequest request) {
    return knowledgeService.createKnowledge(request);
  }

  @PutMapping("/{id}")
  public void updateKnowledge(@PathVariable Long id, @RequestBody KnowledgeUpsertRequest request) {
    knowledgeService.updateKnowledge(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteKnowledge(@PathVariable Long id) {
    knowledgeService.deleteKnowledge(id);
  }
}
