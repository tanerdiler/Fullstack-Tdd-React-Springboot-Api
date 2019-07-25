import React from 'react';
import ReactDOM from 'react-dom';
import { render, fireEvent, waitForElement, cleanup } from '@testing-library/react';
import TransactionForm from './trx-form';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

beforeEach(()=> {
    const mock = new MockAdapter(axios);

    mock.onPost('/transactions', {
        firstname: 'john',
        lastname: 'doe',
        email: 'john.doe@gmail.com',
        productName: 'MacBook Pro',
        price: '8000',
        transactionCode: 'TR456'
    }).reply(200,

        { id: 1, state: 'approved' }

    );

    mock.onPost('/transactions', {
        firstname: 'mary',
        lastname: 'doe',
        email: 'mary.doe@gmail.com',
        productName: 'MacBook Pro',
        price: '8000',
        transactionCode: 'TR457'
    }).reply(200,
        { id: 1, state: 'unapproved' }
    );
})

afterEach(cleanup)

it('display approved notification after saving valid transaction ', async () => {
    const { getByTestId, queryByTestId, container } = render(<TransactionForm/>);
    const firstname = getByTestId('firstname-input');
    const lastname = getByTestId('lastname-input');
    const email = getByTestId('email-input');
    const productName = getByTestId('productname-input');
    const price = getByTestId('price-input');
    const code = getByTestId('code-input');
    const saveButton = getByTestId('save-button');

    fireEvent.change(firstname, { target: { value: 'john' } });
    fireEvent.change(lastname, { target: { value: 'doe' } });
    fireEvent.change(email, { target: { value: 'john.doe@gmail.com' } });
    fireEvent.change(productName, { target: { value: 'MacBook Pro' } });
    fireEvent.change(price, { target: { value: '8000' } });
    fireEvent.change(code, { target: { value: 'TR456' } });

    fireEvent.click(saveButton);

    await waitForElement(()=>getByTestId('transaction-save-approved'));
    expect(queryByTestId('transaction-save-unapproved')).toBeNull();
})

it('display unapproved notification after saving invalid transaction', async () => {
    const { getByTestId,queryByTestId, getByText } = render(<TransactionForm/>);
    const firstname = getByTestId('firstname-input');
    const lastname = getByTestId('lastname-input');
    const email = getByTestId('email-input');
    const productName = getByTestId('productname-input');
    const price = getByTestId('price-input');
    const code = getByTestId('code-input');
    const saveButton = getByTestId('save-button');

    fireEvent.change(firstname, { target: { value: 'mary' } });
    fireEvent.change(lastname, { target: { value: 'doe' } });
    fireEvent.change(email, { target: { value: 'mary.doe@gmail.com' } });
    fireEvent.change(productName, { target: { value: 'MacBook Pro' } });
    fireEvent.change(price, { target: { value: '8000' } });
    fireEvent.change(code, { target: { value: 'TR457' } });

    fireEvent.click(saveButton);

    await waitForElement(()=>getByTestId('transaction-save-unapproved'));
    expect(queryByTestId('transaction-save-approved')).toBeNull();
})
