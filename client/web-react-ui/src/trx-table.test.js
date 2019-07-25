import React from 'react';
import { render, fireEvent, waitForElement, cleanup } from '@testing-library/react';
import TransactionTable from './trx-table';

beforeEach(()=>{

})


afterEach(cleanup)

it('lists transactions', async () => {
    const trxList = [
        {"id":1,"agent":"cindy doe","code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
        {"id":2,"agent":"john doe","code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0},
        {"id":3,"agent":"mary doe","code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":300.0},
        {"id":4,"agent":"mary doe","code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":400.0}
    ];
    const { getByTestId, getByText } = render(<TransactionTable list={trxList}/>);
    const trx1Row = await waitForElement(()=>getByTestId('transaction-1-row'));
    expect(getByTestId('transaction-1-agent').textContent).toEqual('cindy doe');
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
