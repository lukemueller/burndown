package io.pivotal.project

import com.fasterxml.jackson.annotation.JsonRootName
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@JsonRootName("Project")
data class ProjectApiRequestResponseWrapper(val project: Project? = null) {
    override fun toString(): String {
        return "ProjectApiRequestResponseWrapper{" +
                "project=" + project +
                '}'
    }
}
