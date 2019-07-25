import React from 'react';
import logo from './logo.svg';
import axios from 'axios';
import './App.css';

class TransactionTable extends React.Component {

    render() {
        const { list } = this.props;
        return (
            <div data-testid="transaction-list">
                <div>
                    <table>
                        <tbody>
                        {
                            list && list.map(r=><tr key={`transaction-${r.id}-row`} data-testid={`transaction-${r.id}-row`}>
                                <td key={`transaction-${r.id}-agent`} data-testid={`transaction-${r.id}-agent`}>{r.agent}</td>
                                <td key={`transaction-${r.id}-code`} data-testid={`transaction-${r.id}-code`}>{r.code}</td>
                                <td key={`transaction-${r.id}-productname`} data-testid={`transaction-${r.id}-productname`}>{r.productName}</td>
                                <td key={`transaction-${r.id}-price`} data-testid={`transaction-${r.id}-price`}>{r.price}</td>
                                <td key={`transaction-${r.id}-state`} data-testid={`transaction-${r.id}-state`}>{r.state}</td>
                            </tr>)
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default TransactionTable;
