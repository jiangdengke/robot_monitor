package org.jdk.project.dto.knowledge

data class KnowledgeUpsertRequest(
    var title: String? = null,
    var content: String? = null,
    var source: String? = null,
    var knowledgeType: String? = null,
    var processStatus: String? = null,
    var enabled: Boolean? = null,
    var vectorRef: String? = null,
    var createdBy: Long? = null,
    var remark: String? = null,
)
