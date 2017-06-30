export function projects(state = [], action) {
    switch (action.type) {
        case 'GET_PROJECTS':
            console.log(action.payload);
            return action.payload
    }
    return state;
}