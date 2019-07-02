import React from 'react';
import { render, fireEvent, waitForElement, cleanup } from '@testing-library/react';
import TransactionList from './trx-list';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

beforeEach(()=>{

    const mock = new MockAdapter(axios);
    mock.onGet(`/transactions`).reply(function(config) {
        console.log("config", config);
        if (config.params && config.params.firstname === 'mary') { // or check for deep equality with config.params
            return [200,
                {
                "transactions":[
                    {"id":3,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":{"value":300.0}},
                    {"id":4,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":{"value":400.0}}
                ]
            }];
        } else {
            return [200,{
                "transactions":[
                    {"id":1,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR123","state":"APPROVED","productName":"USB Disc","price":{"value":100.0}},
                    {"id":2,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":{"value":200.0}},
                    {"id":3,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":{"value":300.0}},
                    {"id":4,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":{"value":400.0}}
                ]
            }];
        }
    });

})

afterEach(cleanup)

it('lists transactions', async () => {
    const trxList = [
        {"id":1,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR123","state":"APPROVED","productName":"USB Disc","price":{"value":100.0}},
        {"id":2,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":{"value":200.0}},
        {"id":3,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":{"value":300.0}},
        {"id":4,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":{"value":400.0}}
    ];
    const { getByTestId, getByText } = render(<TransactionList/>);
    const trx1Row = await waitForElement(()=>getByTestId('transaction-1-row'));
    expect(getByTestId('transaction-1-agent').textContent).toEqual('john doe');
    expect(getByTestId('transaction-1-code').textContent).toEqual('TR123');
    expect(getByTestId('transaction-1-productname').textContent).toEqual('USB Disc');
    expect(getByTestId('transaction-1-price').textContent).toEqual('100');
    expect(getByTestId('transaction-1-state').textContent).toEqual('APPROVED');

    const trx2Row = await waitForElement(()=>getByTestId('transaction-2-row'));
    expect(getByTestId('transaction-2-agent').textContent).toEqual('john doe');
    expect(getByTestId('transaction-2-code').textContent).toEqual('TR124');
    expect(getByTestId('transaction-2-productname').textContent).toEqual('Flash Disc');
    expect(getByTestId('transaction-2-price').textContent).toEqual('200');
    expect(getByTestId('transaction-2-state').textContent).toEqual('UNAPPROVED');

    const trx3Row = await waitForElement(()=>getByTestId('transaction-3-row'));
    expect(getByTestId('transaction-3-agent').textContent).toEqual('mary doe');
    expect(getByTestId('transaction-3-code').textContent).toEqual('TR125');
    expect(getByTestId('transaction-3-productname').textContent).toEqual('Mac Book Pro');
    expect(getByTestId('transaction-3-price').textContent).toEqual('300');
    expect(getByTestId('transaction-3-state').textContent).toEqual('APPROVED');

    const trx4Row = await waitForElement(()=>getByTestId('transaction-4-row'));
    expect(getByTestId('transaction-4-agent').textContent).toEqual('mary doe');
    expect(getByTestId('transaction-4-code').textContent).toEqual('TR126');
    expect(getByTestId('transaction-4-productname').textContent).toEqual('Mac Mini');
    expect(getByTestId('transaction-4-price').textContent).toEqual('400');
    expect(getByTestId('transaction-4-state').textContent).toEqual('UNAPPROVED');
})

it('filters by purchasing agent', async () => {
    const trxList = [
        {"id":1,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR123","state":"APPROVED","productName":"USB Disc","price":{"value":100.0}},
        {"id":2,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":{"value":200.0}},
        {"id":3,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":{"value":300.0}},
        {"id":4,"agent":{"email":"mary_doe@gmail.com","fullname":"mary doe"},"code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":{"value":400.0}}
    ];
    const { getByTestId, queryByTestId } = render(<TransactionList/>);

    const firstname = getByTestId('filter-firstname-input');
    const lastname = getByTestId('filter-lastname-input');
    const email = getByTestId('filter-email-input');
    const searchButton = getByTestId('search-button');

    fireEvent.change(firstname, { target: { value: 'mary' } });
    fireEvent.change(lastname, { target: { value: 'doe' } });
    fireEvent.change(email, { target: { value: 'mary_doe@gmail.com' } });
    fireEvent.click(searchButton);

    const trx3Row = await waitForElement(()=>getByTestId('transaction-3-row'));
    expect(getByTestId('transaction-3-agent').textContent).toEqual('mary doe');
    expect(getByTestId('transaction-3-code').textContent).toEqual('TR125');
    expect(getByTestId('transaction-3-productname').textContent).toEqual('Mac Book Pro');
    expect(getByTestId('transaction-3-price').textContent).toEqual('300');
    expect(getByTestId('transaction-3-state').textContent).toEqual('APPROVED');

    const trx4Row = await waitForElement(()=>getByTestId('transaction-4-row'));
    expect(getByTestId('transaction-4-agent').textContent).toEqual('mary doe');
    expect(getByTestId('transaction-4-code').textContent).toEqual('TR126');
    expect(getByTestId('transaction-4-productname').textContent).toEqual('Mac Mini');
    expect(getByTestId('transaction-4-price').textContent).toEqual('400');
    expect(getByTestId('transaction-4-state').textContent).toEqual('UNAPPROVED');

    expect(queryByTestId('transaction-1-row')).toBeNull();
    expect(queryByTestId('transaction-2-row')).toBeNull();

})



