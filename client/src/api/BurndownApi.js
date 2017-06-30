import 'whatwg-fetch';

export function getProjects() {
    return fetch(BASE_URL + '/projects', {method: 'GET'});
}
