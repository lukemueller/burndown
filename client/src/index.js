import React from 'react';
import ReactDOM from 'react-dom';
import Burndown from './components/Burndown/Burndown';


const projects = [
    {
        name: 'foo',
        id: 0
    },
    {
        name: 'bar',
        id: 1
    },
    {
        name: 'baz',
        id: 2
    },
    {
        name: 'bat',
        id: 3
    }
];

ReactDOM.render(
    <Burndown {...{projects}}/>,
    document.getElementById('root')
);