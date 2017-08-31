import React, {Component} from "react";
import {VictoryAxis, VictoryChart, VictoryLine, VictoryTheme} from "victory";
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

    formatData() {
        let data = this.data;
        let project = Object.assign({}, this.props.project);

        project.burndown.map(dataItem => {
            data[HISTORICAL].push({x: new Date(dataItem.date), y: dataItem.budgetRemaining});
        });

        this.ticks.push(data[HISTORICAL][data[HISTORICAL].length - 1].x);
        this.ticks.push(data[HISTORICAL][0].x);
    }

    getDateWeeksLater(date, weeks){
        let newDate = new Date(date);
        return newDate.setDate(date.getDate() + 7*weeks);
    }



    renderProject(){
        this.setDefaultData();
        this.formatData();

        let xAxisStartDate = new Date(this.props.project.start_date);

        let xAxisEndDate = new Date(xAxisStartDate);
        xAxisEndDate = xAxisEndDate.setDate(xAxisEndDate.getDate() + 7*8);


        return (
            <VictoryChart padding={{left: 70, top: 15, bottom: 60, right: 60}}  >
                <VictoryAxis
                    label="Date"
                    scale={{x:"time"}}
                    tickValues={this.ticks}
                    domain={[xAxisStartDate, xAxisEndDate]}
                    tickFormat={(t) => t.toLocaleDateString('en-us', {year: 'numeric', month: 'short', day: 'numeric' })}
                     style={{
                         axis: {stroke: "#756f6a"},
                         axisLabel: {fontSize: 15, padding: 30},
                         grid: {stroke: "red"},
                         ticks: {stroke: "grey", size: 5},
                         tickLabels: {fontSize: 8, padding: 5},
                     }}
                    />

                <VictoryAxis dependentAxis
                     domain={[0, this.props.project.budget]}
                     theme={VictoryTheme.material}
                     standalone={false}
                     label="Money Remaining"
                     tickFormat={(t) =>`$${t.toLocaleString()}`}
                     style={{
                         axis: {stroke: "#756f6a"},
                         axisLabel: {fontSize: 15, padding: 50},
                         tickLabels: {fontSize: 8, padding: 5},
                         grid: {stroke: "grey"},
                     }}

                />
                <VictoryLine style={{data: { stroke: "red" }, parent: { border: "1px solid #red"}}} data={this.data[HISTORICAL]}/>
            </VictoryChart>
        )
    }

    render() {
        if(this.props.project !== undefined && this.props.project.burndown !== undefined) {
            return(
                <div className="burndown-chart">
                    <h1>
                        {this.props.project.name}
                    </h1>
                    <div>
                        <h2>Number of people: {this.state.numberOfPeople}</h2>
                        <button onClick={this.decrementStaff}>-</button>
                        <button onClick={this.incrementStaff}>+</button>
                    </div>
                    {this.renderProject()}
                </div>
            )
        }else{
            return(
                <div>
                    <p>No Project Selected</p>
                </div>
            )
        }
    }
}

export default Chart;