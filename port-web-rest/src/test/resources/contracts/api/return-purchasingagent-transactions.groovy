package contracts.api

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
			Return transactions by created specified purchasing agent

			```
			given:
				purchasing agent info
			when:
			    search 
			then:
				we'll return transactions by created specified purchasing agent
			```

			""")

    request {
        method GET()
        url "/transactions"
        headers {
            contentType applicationJson()
        }
        body("""
						{
							"agent": {"firstname":"john", "lastname":"doe", "email":"john.doe@gmail.com"}
						}
					"""
        )
    }

    response {
        status OK()
        body("""
			                {
			                    "transactions":[
			                        {"id":1,"agent":"john doe","code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
			                        {"id":2,"agent":"john doe","code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0}
			                    ]
			                }
					"""
        )
    }
}
