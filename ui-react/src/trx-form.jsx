import React from 'react';
import axios from 'axios';

class TransactionForm extends React.Component {
    state = {
        id: undefined,
        firstname: '',
        lastname: '',
        email: '',
        productName: '',
        price: '',
        code: '',
        state: undefined,
    }

    async getData() {
        const self = this;
        const data = {
            firstname: this.state.firstname,
            lastname: this.state.lastname,
            email: this.state.email,
            productName: this.state.productName,
            price: this.state.price,
            transactionCode: this.state.code
        };
        await axios.post('/transactions', data)
        .then(function(response) {
            console.log(response.data);
            self.setState({id: response.data.id, state: response.data.state});
        });
        //this.setState({ id: result.data.id, state: result.data.state });

    }

    save = () => {
        this.getData();
    }
    changeValue = (e, field) => {
        this.setState({[field]:e.target.value});
    }
    render() {
        return (
            <div>
                <div data-testid="transaction-form">
                    <input data-testid="firstname-input" type='text' name='firstname' value={this.state.firstname} onChange={(event)=>this.changeValue(event, 'firstname')}/>
                    <input data-testid="lastname-input" type='text' name='lastname' value={this.state.lastname} onChange={(event)=>this.changeValue(event, 'lastname')} />
                    <input data-testid="email-input" type='text' name='email' value={this.state.email} onChange={(event)=>this.changeValue(event, 'email')} />
                    <input data-testid="productname-input" type='text' name='productName' value={this.state.productName} onChange={(event)=>this.changeValue(event, 'productName')} />
                    <input data-testid="price-input" type='text' name='price' value={this.state.price} onChange={(event)=>this.changeValue(event, 'price')} />
                    <input data-testid="code-input" type='text' name='code' value={this.state.code} onChange={(event)=>this.changeValue(event, 'code')} />
                    <button data-testid="save-button" onClick={this.save}>Save</button>
                </div>
                {
                    this.state.state && this.state.state==='approved' && ( <div data-testid="transaction-save-approved">
                        Transaction Approved!
                    </div> )
                }
                {
                    this.state.state && this.state.state==='unapproved' && (<div data-testid="transaction-save-unapproved">
                        Transaction Unapproved!
                    </div>)
                }

            </div>
        );
    }
}

export default TransactionForm;

