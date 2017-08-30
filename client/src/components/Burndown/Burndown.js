import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import {getProjects} from '../../api/BurndownApi';
import Chart from "../Chart/Chart";

class Burndown extends Component {
    static propTypes = {
        projects: PropTypes.array
    };

    componentDidMount() {
        const {dispatch} = this.props;
        getProjects().then(response =>
            response.json().then(projects =>
                dispatch({type: 'GET_PROJECTS', payload: projects})
            )
        );
    }

    render() {
        const {projects} = this.props;
        const projectChart = <Chart project={projects[0]}/>
        const projectAnchors = projects.map(({name, id}, key) => {
            return (
                <div key={key}>
                    <a href={`projects/${id}`}>{name}</a>
                </div>
            );
        });

        return (
            <div className="burndown">
                Burndown
                {projectAnchors}
                {projectChart}
            </div>
        );
    }
}

const mapStateToProps = ({projects}) => {
    return {projects: projects};
};

export default connect(mapStateToProps, null)(Burndown);
