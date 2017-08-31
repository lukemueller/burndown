const GET_PROJECTS = "GET_PROJECTS";

let projectsReducer = function projects(state = [], action) {
    switch (action.type) {
        case GET_PROJECTS:
            return action.payload
    }
    return state;
};

export{
    projectsReducer,
    GET_PROJECTS
};