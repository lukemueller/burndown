import React, {Component} from "react";
import {VictoryAxis, VictoryChart, VictoryLine, VictoryTheme} from "victory";
import PropTypes from 'prop-types';

class Chart extends Component{
    static propTypes = {
        project: PropTypes.object
    };

    burndown = [];
    projection = [];
    ticks = {};

    constructor(props){
        super(props);

        this.setDefaultData();
    }

    setDefaultData(){
        this.ticks= {
            x: [],
            y: []
        };
        this.burndown = [];
        this.projection = [];
    }

    formatData() {
        let project = Object.assign({}, this.props.project);

        project.burndown.map(dataItem => {
            this.burndown.push({x: new Date(dataItem.date), y: dataItem.budget_remaining});
        });

        this.projection.push(this.burndown[this.burndown.length - 1]);
        this.projection.push({x: new Date(project.projected_end_date), y: 0});

        this.ticks.x.push(this.projection[this.projection.length - 1].x);
        this.ticks.x.push(this.projection[0].x);
        this.ticks.x.push(this.burndown[0].x);

        this.ticks.y.push(this.projection[this.projection.length - 1].y);
        this.ticks.y.push(this.projection[0].y);
        this.ticks.y.push(this.burndown[0].y)
    }

    renderProject(){
        this.setDefaultData();
        this.formatData();

        let xAxisStartDate = new Date(this.props.project.start_date);
        let xAxisEndDate = new Date(this.props.project.projected_end_date);

        return (
            <VictoryChart theme={VictoryTheme.material} height={400} width={600}
                          padding={{left: 70, right:50, top:10, bottom: 50}}>
                <VictoryAxis
                    scale={{x:"time"}}
                    tickValues={this.ticks.x}
                    tickFormat={(t) => t.toLocaleDateString('en-us', {year: 'numeric', month: 'numeric', day: 'numeric' })}/>

                <VictoryAxis dependentAxis
                     tickValues={this.ticks.y}
                     tickFormat={(t) =>`$${t.toLocaleString()}`}/>
                <VictoryLine style={{data: { stroke: "red" }, parent: { border: "1px solid #red"}}} data={this.burndown}/>
                <VictoryLine style={{data: { stroke: "blue" }, parent: { border: "1px solid #red"}}} data={this.projection}/>
            </VictoryChart>
        )
    }

    render() {
        if(this.props.project !== undefined && this.props.project.burndown !== undefined) {
            return(
                <div className="burndown-chart">
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