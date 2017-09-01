import {getProject} from "../api/BurndownApi";
const GET_PROJECT = "GET_PROJECT";

const getProjectAction = function(projectId, numberOfEmployees = -1){
    let request = getProject(projectId, numberOfEmployees);

    return{
        type: GET_PROJECT,
        payload: request
    }
};

export{
    getProjectAction,
    GET_PROJECT
}