# KATA-2
TDD ile REACT UI Geliştirme - PART-1
## AMAÇ
TDD ile UI bileşenleri yazacağız. Mission-1'de karar verilen contract'lara sadık kalarak rest çağrımlarını yapacağız. Mission-1'de oluşturulan
Stub Server kullanılarak Backend yazılmasına gerek kalmadan Frontend'i çalışır hale getireceğiz.
## YÖNLENDIRMELER
- "npm run test" komutunu kullanarak testleri çalıştırabilirsiniz
## DEFINITION OF DONE
- TDD ile UI geliştirmeleri yapılmalı
- Rest servis çağrımları mock'lanmalı
- Mission-1'de oluşturulan Stub Runner entegre edilmeli
## GÖREVLER
1. Analiz dokümanındaki form ve tablo geçişlerini kurgulayalım
1.1. Hello World! text'ine içeren bir elementin varlığını test edelim
-- client/web-react-ui/src/App.test.js
```
it('says Hellow World!', async () => {
    const { getByText } = render(<App />);
    await waitForElement(()=>getByText("Hello World!"));
})
```
1.2. Uygulama ilk açıldığında form ekranı gelmeli
-- client/web-react-ui/src/App.test.js
```
it('displays transaction form by default', async () => {
    const { getByTestId } = render(<App />);
    await waitForElement(()=>getByTestId('transaction-form'));
});
```
1.3. List butonuna basıldığında transaction listeleme ekranı gelmeli; form ekranı kaybolmalı
-- client/web-react-ui/src/App.test.js
```
it('switch to transaction table when list button clicked', async () => {
  const { getByTestId, queryByTestId, getByText } = render(<App />);
  const listButton = await waitForElement(()=>getByTestId('list-button'));
  fireEvent.click(listButton);
  await waitForElement(()=>getByTestId('transaction-list'));
  expect(queryByTestId('transaction-form')).toBeNull();
})
```
1.4. New butonuna basıldığında form ekranı yeniden gelmeli; listeleme ekranı kaybolmalı
-- client/web-react-ui/src/App.test.js
```
it('switch to transaction form again when new button clicked', async () => {
  const { getByTestId, queryByTestId, getByText } = render(<App />);

  const listButton = await waitForElement(()=>getByTestId('list-button'));
  fireEvent.click(listButton);

  const newButton = await waitForElement(()=>getByTestId('new-button'));
  fireEvent.click(newButton);

  await waitForElement(()=>getByTestId('transaction-form'));
  expect(queryByTestId('transaction-list')).toBeNull();
})
```

2. Transaction bilgisi kaydedildiğinde eğer onaylandıysa, "Kaydedilen satın alım işleminiz onaylanmıştır" mesajı gösterilmeli
-- client/web-react-ui/src/trx-form.test.js
```
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
    fireEvent.change(productName, { targe   t: { value: 'MacBook Pro' } });
    fireEvent.change(price, { target: { value: '8000' } });
    fireEvent.change(code, { target: { value: 'TR456' } });

    fireEvent.click(saveButton);

    await waitForElement(()=>getByTestId('transaction-save-approved'));
    expect(queryByTestId('transaction-save-unapproved')).toBeNull();
})
```

3. Transaction bilgisi kaydedildiğinde eğer onaylanmadıysa, "Kaydedilen satın alım işleminiz onaylanmamıştır" mesajı gösterilmeli
-- client/web-react-ui/src/trx-form.test.js
```
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
```
4. Backend Api Rest servislerine istek yapılarak transaction kaydedilmeli ve onay duruma göre ilgili mesaj gösterilmeli
	* Axios modülü kullanılarak Rest istekleri yapılacaktır.
	* Birim test yaptığımızda axis çağrıları istediğimiz cevabı bize döndürecek şekilde mocklanmalı
	* Servis Request ve Response yapısı Contract'lar oluşturulurken belirlenmişti
	
-- client/web-react-ui/src/trx-form.test.js
  * Satın Alım'ın onaylandığı test için yapılan çağrıyı mocklayalım
```
beforeEach(()=>{
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
     }
```
  * Satın Alım'ın onaylanmadığı test için yapılan çağrıyı mocklayalım
```
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
```
5. Satın Alım Tablosu verilen işlemleri listelemeli
```
-- trx-list.test.js

 it('lists transactions', async () => {
    const trxList = [
        {"id":1,"agent":"cindy doe","code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
        {"id":2,"agent":"john doe","code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0},
        {"id":3,"agent":"mary doe","code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":300.0},
        {"id":4,"agent":"mary doe","code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":400.0}
    ];
    const { getByTestId, getByText } = render(<TransactionList list={trxList}/>);
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
```
6. Listeleme ekranı açıldığında default olarak kayıtlı tüm satın alım işlemleri listelenmeli
-- trx-list.test.js
   * Mock axios post call
```
const mock = new MockAdapter(axios);
mock.onGet(`/transactions`).reply(function(config) {
    return [200,{
        "transactions":[
            {"id":1,"agent":"john doe","code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
            {"id":2,"agent":"john doe","code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0},
            {"id":3,"agent":"mary doe","code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":300.0},
            {"id":4,"agent":"mary doe","code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":400.0}
        ]
    }];
});
```
7. Listenin props yerine state'den alınmasından dolayı birinci test patlayacaktır. Burada yapılması gereken doğru davranış list-table bileşeni oluşturmaktır ve birinci testi bu bileşinin testi haline getirmek olacaktır.
-- trx-table.test.js
```
import React from 'react';
import { render, fireEvent, waitForElement, cleanup } from '@testing-library/react';
import TransactionTable from './trx-table';

beforeEach(()=>{

})


afterEach(cleanup)

it('lists transactions', async () => {
    const trxList = [
        {"id":1,"agent":"cindy doe"},"code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
        {"id":2,"agent":"john doe"},"code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0},
        {"id":3,"agent":"mary doe"},"code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":300.0},
        {"id":4,"agent":"mary doe"},"code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":400.0}
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
```
--trx-list.test.js dosyasından ilk testi silelim
--trx-list.jsx dosyasında aşağıdaki değişiklikleri yapalım
```
<div data-testid="transaction-list">
    <TransactionTable list={list}/>
</div>
```

8. Satın Alma Sorumlusu'nun adı, soyadı ve email adresi girilerek satın alım işlemleri filtrelenmeli
   
- trx-list.test.js dosyasında beforeEach method içeriğin, aşağıdaki  filtreleme 
```
beforeEach(()=>{

  const mock = new MockAdapter(axios);
  mock.onGet(`/transactions`).reply(function(config) {
      console.log("config", config);

      // AxiosMock, URL eslestirmesi yaparken get parametrelerine bakmiyor
      if (config.params && config.params.firstname === 'mary') { // or check for deep equality with config.params
          return [200,
              {
              "transactions":[
                  {"id":3,"agent":"mary doe","code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":300.0},
                  {"id":4,"agent":"mary doe","code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":400.0}
              ]
          }];
      } else {
          return [200,{
              "transactions":[
                  {"id":1,"agent":"john doe"},"code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
                  {"id":2,"agent":"john doe"},"code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0},
                  {"id":3,"agent":"mary doe"},"code":"TR125","state":"APPROVED","productName":"Mac Book Pro","price":300.0},
                  {"id":4,"agent":"mary doe"},"code":"TR126","state":"UNAPPROVED","productName":"Mac Mini","price":400.0}
              ]
          }];
      }
  });
})
```
   		
- trx-list.test.js 
```   
it('filters by purchasing agent', async () => {
    
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
```

