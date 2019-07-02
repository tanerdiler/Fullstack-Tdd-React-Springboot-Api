import React from 'react';
import axios from "axios/index";

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
                <table>
                    <tbody>
                    {
                        list && list.map(r=><tr key={`transaction-${r.id}-row`} data-testid={`transaction-${r.id}-row`}>
                                        <td key={`transaction-${r.id}-agent`} data-testid={`transaction-${r.id}-agent`}>{r.agent.fullname}</td>
                                        <td key={`transaction-${r.id}-code`} data-testid={`transaction-${r.id}-code`}>{r.code}</td>
                                        <td key={`transaction-${r.id}-productname`} data-testid={`transaction-${r.id}-productname`}>{r.productName}</td>
                                        <td key={`transaction-${r.id}-price`} data-testid={`transaction-${r.id}-price`}>{r.price.value}</td>
                                        <td key={`transaction-${r.id}-state`} data-testid={`transaction-${r.id}-state`}>{r.state}</td>
                                    </tr>)
                    }
                    </tbody>
                </table>
            </div>
        );
    }
}

export default TransactionList;

