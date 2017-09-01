/**
 * Created by devondapuzzo on 8/31/17.
 */
import {GET_PROJECT} from "../actions/getProjectAction";

export default function projectReducer(state = {}, action) {
    switch (action.type) {
        case GET_PROJECT:
            return action.payload.data.project
    }
    return state;
};

