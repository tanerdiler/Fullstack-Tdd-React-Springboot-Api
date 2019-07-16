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


