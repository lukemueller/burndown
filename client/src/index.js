import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {createStore, combineReducers, applyMiddleware} from 'redux';

import thunk from 'redux-thunk';

import {projects} from './reducers/ProjectsReducer';
import Burndown from './components/Burndown/Burndown';

const store = createStore(
    combineReducers({projects}),
    applyMiddleware(thunk)
);

ReactDOM.render(
    <Provider store={store}>
        <Burndown/>
    </Provider>,
    document.getElementById('root')
);