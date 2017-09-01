import {GET_PROJECTS} from "../actions/getProjectsAction";

export default function projectsReducer(state = [], action) {
    switch (action.type) {
        case GET_PROJECTS:
            return action.payload.data
    }
    return state;
};

