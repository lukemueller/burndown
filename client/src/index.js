import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {createStore, applyMiddleware} from 'redux';

import thunk from 'redux-thunk';

import reducer from './reducers';
import Burndown from './components/Burndown/Burndown';

const createStoreWithMiddleware = applyMiddleware(thunk)(createStore);

ReactDOM.render(
    <Provider store={createStoreWithMiddleware(reducer)}>
        <Burndown/>
    </Provider>,
    document.getElementById('root')
);