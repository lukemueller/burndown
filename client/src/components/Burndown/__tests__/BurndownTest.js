import React from 'react';
import {shallow} from 'enzyme';

import Burndown from '../Burndown';

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
        const wrapper = shallow(<Burndown {...{projects}} />);

        const anchors = wrapper.find('a');
        expect(anchors.length).toEqual(2);
        expect(anchors.at(0).text()).toEqual("Test project 1");
        expect(anchors.at(0).props().href).toEqual("projects/Test project 1");
        expect(anchors.at(1).text()).toEqual("Test project 2");
        expect(anchors.at(1).props().href).toEqual("projects/Test project 2");
    });
});

