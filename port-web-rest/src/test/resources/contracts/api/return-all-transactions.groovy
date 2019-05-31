package contracts.api

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
Return all transactions

```
given:
	search criteria
when:
    search 
then:
	we'll return all transactions ever saved
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
				"agent": null
			}
		"""
        )
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body("""
                {
                    "agentTransactions":[
                        {
                            "agent":{"email":"john_doe@gmail.com","fullname":"john doe"},
                            "transactions":[
                                {"id":null,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR123","state":"WAITING_APPROVE","productName":"USB Disc","price":{"value":100.0}},
                                {"id":null,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR124","state":"WAITING_APPROVE","productName":"FLash Disc","price":{"value":200.0}}
                            ]
                         }
                     ]
                }
		"""
        )
    }
}