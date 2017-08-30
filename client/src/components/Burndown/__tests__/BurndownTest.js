import React from 'react';
import {mount, shallow} from 'enzyme';

import Burndown from '../Burndown';
import {Provider} from "react-redux";
import {applyMiddleware, combineReducers, createStore} from "redux";
import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';

const mockStore = configureStore([thunk]);

describe('Burndown Component', () => {
    it('renders links for each project in the project list', () => {
        const projects = [
            {
                id: 14,
                name: "Test project 1"
            },
            {
                id: 15,
                name: "Test project 2"
            }
        ];

        let store = mockStore({projects});
        const wrapper = mount(<Burndown store={store} />);

        const anchors = wrapper.find('a');

        expect(anchors.length).toEqual(2);
        expect(anchors.at(0).text()).toEqual("Test project 1");
        expect(anchors.at(0).props().href).toEqual("projects/14");
        expect(anchors.at(1).text()).toEqual("Test project 2");
        expect(anchors.at(1).props().href).toEqual("projects/15");
    });
});

