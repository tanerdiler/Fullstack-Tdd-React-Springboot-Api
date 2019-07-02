import React from 'react';

class HelloMessage extends React.Component {
    render() {
        const { name } = this.props;
        return (
            <div>hello {name}</div>
        );
    }
}

export default HelloMessage;
