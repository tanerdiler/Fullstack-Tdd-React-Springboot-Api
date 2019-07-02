import React from 'react';
import logo from './logo.svg';
import './App.css';
import TransactionForm from "./trx-form";
import TransactionList from "./trx-list";


class App extends React.Component {
    state = {
        screen: 'form',
    }

    clickNewButton = () => {
        this.setState({screen:'form'});
    }

    clickListButton = () => {
        this.setState({screen:'list'});
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <p>
                        Edit <code>src/App.js</code> and save to reload.
                    </p>
                    <a
                        className="App-link"
                        href="https://reactjs.org"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                </header>
                <button data-testid='new-button' onClick={this.clickNewButton}>Transaction  New</button>
                <button data-testid='list-button' onClick={this.clickListButton}>Transaction List</button>
                {this.state.screen === 'form' && <TransactionForm/>}
                {this.state.screen === 'list' && <TransactionList/>}
            </div>
        );
    }
}

export default App;
