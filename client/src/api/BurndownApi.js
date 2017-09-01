import 'whatwg-fetch';

export function getProjects() {
    return fetch(`${BASE_URL}/projects`, {method: 'GET'});
}

export function getProject(projectId, numberOfEmployees=-1){
    const query = numberOfEmployees > 0 ? `?numberOfEmployees=${numberOfEmployees}` : "";
    return fetch(`${BASE_URL}/projects/${projectId}${query}`, {method: 'GET'})
}
