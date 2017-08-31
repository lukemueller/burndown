import React from 'react';
import {mount} from 'enzyme';

import Chart from '../Chart';


describe('Chart Component', () => {
    it('creates a data with the historical burndown data and projects the data until the end', () => {
        const project = {
            "id": 1,
            "name": "Kitten Mittens",
            "hourly_rate": 100,
            "start_date": "2017-01-01",
            "budget": 500000,
            "burndown": [500000, 484000, 460000, 420000, 400000]
        };

        const wrapper = mount(<Chart project={project} />);
        const chart = wrapper.find(Chart);
        const data = chart.nodes[0].data;

        expect(data.HISTORICAL[0].y).toEqual(project.budget);
        expect(data.HISTORICAL[data.HISTORICAL.length-1].y).toEqual(400000);
        expect(data.HISTORICAL.length).toEqual(project.burndown.length);
    });
});

