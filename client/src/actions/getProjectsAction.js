import {getProjects} from "../api/BurndownApi";
const GET_PROJECTS = "GET_PROJECTS";

const getProjectsAction = function(){
    let request = getProjects();

    return{
        type: GET_PROJECTS,
        payload: request
    }
};

export{
    getProjectsAction,
    GET_PROJECTS
}