import React from 'react';
import logo from './logo.svg';
import './App.css';

class App extends React.Component {
    state = {
        screen: 'form',
    }

    clickListButton = () => {
        this.setState({screen:'list'});
    }

    clickNewButton = () => {
        this.setState({screen:'form'});
    }

    render() {
        return (
            <div className="App">
                <div>Hello World!</div>
                <button data-testid="new-button" onClick={this.clickNewButton}>New</button>
                <button data-testid="list-button" onClick={this.clickListButton}>List</button>
                { this.state.screen === 'form' && <div data-testid="transaction-form"></div> }
                { this.state.screen === 'list' && <div data-testid="transaction-list"></div> }
            </div>
        );
    }
}

export default App;
