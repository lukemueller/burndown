import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import _ from "lodash";

import Chart from "../Chart/Chart";
import {getProjectAction} from "../../actions/getProjectAction";
import {bindActionCreators} from "redux";
import {getProjectsAction} from "../../actions/getProjectsAction";


class Burndown extends Component {
    static propTypes = {
        projects: PropTypes.array,
        project: PropTypes.object
    };

    constructor(props){
        super(props);

        this.switchProject = this.switchProject.bind(this);
        this.incrementNumberOfEmployees = this.incrementNumberOfEmployees.bind(this);
        this.decrementNumberOfEmployees = this.decrementNumberOfEmployees.bind(this);
    }

    componentDidMount() {
        this.props.getProjectsAction();
    }

    render() {
        const {projects, project} = this.props;

        const projectChart = <Chart project={project}/>;
        const projectSelectors = projects.map(({name, id}, key) => {
            return (
                <span key={key}>
                    <button className="btn btn-primary" id={id} onClick={this.switchProject}>{name}</button>
                </span>
            );
        });
        const projectControls = this.renderProjectControls();

        return (
            <div className="burndown">
                <h2>Burndown</h2>
                {projectSelectors}
                {projectControls}
                {projectChart}
            </div>
        );
    }

    renderProjectControls() {
        if(!_.isEmpty(this.props.project)){
            return (
                <div>
                    <h3>{this.props.project.name}</h3>
                    <div>
                        <h4>Number of people: {this.props.project.number_of_employees}</h4>
                        <button className="btn btn-light" onClick={this.decrementNumberOfEmployees}>-</button>
                        <button className="btn btn-light" onClick={this.incrementNumberOfEmployees}>+</button>
                    </div>
                </div>
            )
        }
    }

    switchProject(event){
        event.preventDefault();
        this.fetchProject(event.target.id);
    };

    fetchProject(projectId, numberOfEmployees=-1){
        this.props.getProjectAction(projectId, numberOfEmployees);
    }

    incrementNumberOfEmployees(){
        this.fetchProject(this.props.project.id, this.props.project.number_of_employees + 1)
    }

    decrementNumberOfEmployees(){
        if(this.props.project.number_of_employees > 1){
            this.fetchProject(this.props.project.id, this.props.project.number_of_employees - 1)
        }
    }
}

const mapStateToProps = ({projects, project}) => {
    return {projects, project};
};

const mapDispatchToProps = (dispatch) =>{
    return bindActionCreators({getProjectAction, getProjectsAction}, dispatch)
};

export default connect(mapStateToProps, mapDispatchToProps)(Burndown);
