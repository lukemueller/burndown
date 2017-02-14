package io.pivotal.project;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Project")
public class CreateProjectApiRequest {
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "CreateProjectApiRequest{" +
            "project=" + project +
            '}';
    }
}
