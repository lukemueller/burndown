import React, {Component} from 'react';
import PropTypes from 'prop-types'

class Burndown extends Component {
    static propTypes = {
        projects: PropTypes.array.isRequired
    };

    render() {
        const {projects} = this.props;
        const projectAnchors = projects.map(({name}, key) => {
            return (
                <div key={key}>
                    <a href={`projects/${name}`}>{name}</a>
                </div>
            );
        });

        return (
            <div className="burndown">Burndown
                {projectAnchors}
            </div>
        );
    }
}

export default Burndown;
