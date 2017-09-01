import React from 'react';
import {mount} from 'enzyme';

import Chart from '../Chart';


describe('Chart Component', () => {
    it('creates a data array with the historical burndown data and projects the data until the end', () => {
        const project = {
            "id": 1,
            "name": "Kitten Mittens",
            "hourly_rate": 100,
            "start_date": "2017-01-01",
            "projected_end_date": "2017-04-01",
            "budget": 500000,
            "burndown": [
                {budget_remaining: 500000, date: "2017-01-01"},
                {budget_remaining: 484000, date: "2017-01-07"},
                {budget_remaining: 460000, date: "2017-01-14"},
                {budget_remaining: 420000, date: "2017-01-21"},
                {budget_remaining: 400000, date: "2017-01-28"}
            ]
        };

        const wrapper = mount(<Chart project={project} />);
        const chart = wrapper.find(Chart);
        const {burndown, projection} = chart.nodes[0];

        expect(burndown[0].y).toEqual(project.budget);
        expect(burndown[burndown.length-1].y).toEqual(400000);
        expect(burndown.length).toEqual(project.burndown.length);

        expect(projection.length).toEqual(2);
        expect(projection[0].y).toEqual(burndown[burndown.length-1].y);
        expect(projection[1].y).toEqual(0);
    });
});

