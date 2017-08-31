import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import {getProjects, getProject} from '../../api/BurndownApi';
import Chart from "../Chart/Chart";
import {GET_PROJECTS} from "../../reducers/ProjectsReducer";
import {GET_PROJECT} from "../../reducers/ProjectReducer";


class Burndown extends Component {
    static propTypes = {
        projects: PropTypes.array,
        project: PropTypes.object
    };

    constructor(props){
        super(props);

        this.state = {selectedProjectId: null};

        this.getSelectedProject = this.getSelectedProject.bind(this);
    }

    componentDidMount() {
        const {dispatch} = this.props;
        getProjects().then(response =>
            response.json().then(projects =>
                dispatch({type: GET_PROJECTS, payload: projects})
            )
        );
    }

    render() {
        const {projects, project} = this.props;

        const projectChart = <Chart project={project}/>;
        const projectSelectors = projects.map(({name, id}, key) => {
            return (
                <span key={key}>
                    <button className="btn btn-primary" id={id} onClick={this.getSelectedProject}>{name}</button>
                </span>
            );
        });

        return (
            <div className="burndown">
                <h2>
                    Burndown
                </h2>
                <div>
                    {projectSelectors}
                </div>
                {projectChart}
            </div>
        );
    }

    getSelectedProject(event){
        event.preventDefault();
        const {dispatch} = this.props;

        getProject(event.target.id).then(response =>
            response.json().then(projects =>
                dispatch({type: GET_PROJECT, payload: projects})
            )
        );
    };
}

const mapStateToProps = ({projects, project}) => {
    return {projects, project};
};

export default connect(mapStateToProps, null)(Burndown);
