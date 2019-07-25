import React from 'react';
import logo from './logo.svg';
import axios from 'axios';
import './App.css';
import TransactionTable from './trx-table';

class TransactionList extends React.Component {

    state = {
        firstname: undefined,
        lastname: undefined,
        email: undefined,
        list: [],
    }

    componentDidMount = async () => {
        const self = this;
        await axios.get('/transactions')
            .then(function(response) {
                self.setState({list: response.data.transactions});
            });
    }

    search = async () => {
        const self = this;
        const {firstname, lastname, email} = this.state;
        await axios.get('/transactions',{params:{firstname, lastname, email}})
            .then(function(response) {
                self.setState({list: response.data.transactions});
            });
    }

    changeValue = (e, field) => {
        this.setState({[field]:e.target.value});
    }

    render() {
        const { list } = this.state;
        return (
            <div data-testid="transaction-list">
                <div data-testid="search-panel">
                    <input data-testid="filter-firstname-input" type='text' name='firstname' value={this.state.firstname} onChange={(event)=>this.changeValue(event, 'firstname')}/>
                    <input data-testid="filter-lastname-input" type='text' name='lastname' value={this.state.lastname} onChange={(event)=>this.changeValue(event, 'lastname')}/>
                    <input data-testid="filter-email-input" type='text' name='email' value={this.state.email} onChange={(event)=>this.changeValue(event, 'email')}/>
                    <button data-testid="search-button" onClick={this.search}>Search</button>
                </div>
                <TransactionTable list={list} />
            </div>
        );
    }
}

export default TransactionList;
