import 'whatwg-fetch';

export function getProjects() {
    return fetch(`${BASE_URL}/projects`, {method: 'GET'});
}

export function getProject(projectId){
    return fetch(`${BASE_URL}/projects/${projectId}`, {method: 'GET'})
}
