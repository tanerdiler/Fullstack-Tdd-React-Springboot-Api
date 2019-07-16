1. Analiz dokümanındaki form ve tablo geçişlerini kurgulayalım
2. Hello World! text'ine içeren bir elementin varlığını test edelim

	-- App.test.js ---> Fail

		it('says Hellow World!', async () => {
		    const { getByText } = render(<App />);
		    await waitForElement(()=>getByText("Hello World!"));
		})

	-- App.js ---> Success

	   <div>Hello World!</div>
3. Uygulama ilk açıldığında form ekranı gelmeli

	-- App.test.js ---> Fail

		it('displays transaction form by default', async () => {
		    const { getByTestId } = render(<App />);
		    await waitForElement(()=>getByTestId('transaction-form'));
		});

	-- App.js

	   <div data-testid="transaction-form"></div>

4. List butonuna basıldığında transaction listeleme ekranı gelmeli; form ekranı kaybolmalı

    -- App.test.js ---> Fail

	    it('switch to transaction table when list button clicked', async () => {
		    const { getByTestId, queryByTestId, getByText } = render(<App />);
		    const listButton = await waitForElement(()=>getByTestId('list-button'));
		    fireEvent.click(listButton);
		    await waitForElement(()=>getByTestId('transaction-list'));
		    expect(queryByTestId('transaction-form')).toBeNull();
		})

	-- App.js
	. list-button butonunu ekleyelim
	  	<button data-testid="list-button">List</button>
	. transaction-list div elementini ekleyelim
	  	<div data-testid="transaction-list"></div>
	. testi geçebilmek için transaction-form elementini silelim ama bir önceki testten dolayı patlayacaktır
	. transaction-form elementini gizlemek için screen state'ini ekleyelim
		  state = {
	        screen: 'form',
	      }
	. list-button butonuna tıklandığında screen state'ine 'list' değerini atayalım
		  clickListButton = () => {
        	this.setState({screen:'list'});
    	  }
    . transaction-form elementinin gösterilmesini karar veren kontrolü ekleyelim
          { this.state.screen === 'form' && <div data-testid="transaction-form"></div> }
    . SUCCESS
    . Kodun Tümü
	        state = {
		        screen: 'form',
		    }

		    clickListButton = () => {
		        this.setState({screen:'list'});
		    }

		    render() {
		        return (
		            <div className="App">
		                <div>Hello World!</div>
		                <button data-testid="list-button" onClick={this.clickListButton}>List</button>
		                { this.state.screen === 'form' && <div data-testid="transaction-form"></div> }
		                <div data-testid="transaction-list"></div>
		            </div>
		        );
		    }
5. New butonuna basıldığında form ekranı yeniden gelmeli; listeleme ekranı kaybolmalı
	 
    -- App.test.js ---> Fail

	    it('switch to transaction form again when new button clicked', async () => {
		    const { getByTestId, queryByTestId, getByText } = render(<App />);

		    const listButton = await waitForElement(()=>getByTestId('list-button'));
		    fireEvent.click(listButton);

		    const newButton = await waitForElement(()=>getByTestId('new-button'));
		    fireEvent.click(newButton);

		    await waitForElement(()=>getByTestId('transaction-form'));
		    expect(queryByTestId('transaction-list')).toBeNull();
		})

	-- App.js
	. new-button butonu ekleyelim
	  	<button data-testid="new-button">New</button>
	. testi geçebilmek için transaction-list elementini silelim ama bir önceki testten dolayı patlayacaktır
	. new-button butonuna tıklandığında screen state'ine 'form' değerini atayalım
		  clickNewButton = () => {
        	this.setState({screen:'form'});
    	  }
    . transaction-list elementinin gösterilmesini karar veren kontrolü ekleyelim
          { this.state.screen === 'list' && <div data-testid="transaction-list"></div> }
    . SUCCESS
