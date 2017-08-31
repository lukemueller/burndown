/**
 * Created by devondapuzzo on 8/31/17.
 */
const GET_PROJECT = "GET_PROJECT";

let projectReducer = function projects(state = {}, action) {
    switch (action.type) {
        case GET_PROJECT:
            return action.payload.project
    }
    return state;
};

export {
    GET_PROJECT,
    projectReducer
}