package io.pivotal.project;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonRootName("Project")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectApiRequestResponseWrapper {
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "ProjectApiRequestResponseWrapper{" +
            "project=" + project +
            '}';
    }
}
