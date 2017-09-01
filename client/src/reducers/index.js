import { combineReducers } from 'redux';
import projectsReducer from "./ProjectsReducer"
import projectReducer from "./ProjectReducer"

const rootReducer = combineReducers({
    project: projectReducer,
    projects: projectsReducer
});

export default rootReducer;
