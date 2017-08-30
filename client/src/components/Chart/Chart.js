/**
 * Created by devondapuzzo on 8/30/17.
 */

import React, {Component} from "react";
import {VictoryAxis, VictoryChart, VictoryLine} from "victory";
import PropTypes from 'prop-types';

const PROJECTED = "PROJECTED";
const HISTORICAL = "HISTORICAL";


class Chart extends Component{
    static propTypes = {
        project: PropTypes.object
    };

    data = {};
    ticks = [];

    constructor(props){
        super(props);

        this.state={numberOfPeople: 6};

        this.setDefaultData();

        this.incrementStaff = this.incrementStaff.bind(this);
        this.decrementStaff = this.decrementStaff.bind(this);
    }

    incrementStaff(){
        this.setState({numberOfPeople: this.state.numberOfPeople + 1});
    }

    decrementStaff(){
        if(this.state.numberOfPeople > 1){
            this.setState({numberOfPeople: this.state.numberOfPeople - 1});
        }
    }

    setDefaultData(){
        this.ticks= [];
        this.data[PROJECTED] = [];
        this.data[HISTORICAL]= [];
    }

    formatData(){
        let data = this.data;
        let project = Object.assign({}, this.props.project);
        let date = new Date(this.props.project.start_date);

        //Canned data - remove when real burndown api works
        project.burndown = [
            project.budget,
            project.budget - (project.hourly_rate * 8 * 5 * 6 * .4),
            project.budget - (project.hourly_rate * 8 * 5 * 6 * 1.9),
            project.budget - (project.hourly_rate * 8 * 5 * 6 * 2.5),
            project.budget - (project.hourly_rate * 8 * 5 * 6 * 3.4)
        ];

        project.burndown.map(dataItem =>{
            data[HISTORICAL].push({x: new Date(date), y: dataItem});
            date.setDate(date.getDate() + 7);
        });

        if(data[HISTORICAL].length === 0) {
            data[HISTORICAL].push({x: new Date(date), y: project.budget})
        }else{
            date.setDate(date.getDate() - 7);
        }

        project.budget = data[HISTORICAL][data[HISTORICAL].length - 1].y;
        data[PROJECTED].unshift(data[HISTORICAL][data[HISTORICAL].length - 1]);

        this.buildOutData(project, PROJECTED, date, this.state.numberOfPeople * project.hourly_rate * 8 * 5);
        this.buildOutData(project, PROJECTED, date, 0, 7);

        this.ticks.push(data[HISTORICAL][data[HISTORICAL].length - 1].x);
        this.ticks.push(data[HISTORICAL][0].x);
        this.ticks.push(data[PROJECTED][data[PROJECTED].length -1].x);
    }


    buildOutData(project, key, date, threshold=0, divisor = 1){
        while(project.budget > threshold){
            this.data[key].push({x: new Date(date), y: project.budget});
            project.budget -= this.state.numberOfPeople * project.hourly_rate * 8 * 5 /divisor;
            date.setDate(date.getDate() + 7/divisor);
        }
    }

    renderProject(){
        this.setDefaultData();
        this.formatData();

        return (
            <VictoryChart padding={{left: 60, top: 0, bottom: 60, right: 60}}  >
                <VictoryAxis
                    label="Time Remaining"
                    scale={{x:"time"}}
                    tickValues={this.ticks}
                    tickFormat={(t) => t.toLocaleDateString('en-us', {year: 'numeric', month: 'short', day: 'numeric' })}
                    style={{
                        axis: {stroke: "#756f6a"},
                        axisLabel: {fontSize: 20, padding: 30},
                        grid: {stroke: (t) => t > 0.5 ? "red" : "grey"},
                        ticks: {stroke: "grey", size: 5},
                        tickLabels: {fontSize: 8, padding: 5},
                    }}/>
                <VictoryLine style={{data: { stroke: "red" }, parent: { border: "1px solid #red"}}} data={this.data[HISTORICAL]}/>
                <VictoryLine style={{data: { stroke: "blue" }, parent: { border: "1px solid #blue"}}} data={this.data[PROJECTED]}/>
            </VictoryChart>
        )
    }

    render() {
        if(this.props.project && this.props.project.burndown) {

            return(
                <div className="burndown-chart">
                <h1>
                    {this.props.project.name}
                </h1>
                <div>
                    <h2>
                        Number of people: {this.state.numberOfPeople}
                    </h2>
                    <button onClick={this.decrementStaff}>
                        -
                    </button>
                    <button onClick={this.incrementStaff}>
                        +
                    </button>
                </div>
                {this.renderProject()}
            </div>
            )
        }else{
            return(
                <div>
                    <p>no project selected</p>
                </div>
            )
        }
    }
}

export default Chart;