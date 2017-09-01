import 'whatwg-fetch';
import axios from 'axios';

export function getProjects() {
    return axios.get(`${BASE_URL}/projects`);
}

export function getProject(projectId, numberOfEmployees=-1){
    const query = numberOfEmployees > 0 ? `?numberOfEmployees=${numberOfEmployees}` : "";
    return axios.get(`${BASE_URL}/projects/${projectId}${query}`);
}
