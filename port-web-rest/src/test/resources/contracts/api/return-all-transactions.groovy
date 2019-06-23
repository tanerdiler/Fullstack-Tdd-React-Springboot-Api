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
                    "transactions":[
                        {"id":1,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR123","state":"APPROVED","productName":"USB Disc","price":{"value":100.0}},
                        {"id":2,"agent":{"email":"john_doe@gmail.com","fullname":"john doe"},"code":"TR124","state":"UNAPPROVED","productName":"FLash Disc","price":{"value":200.0}}
                    ]
                }
		"""
        )
    }
}