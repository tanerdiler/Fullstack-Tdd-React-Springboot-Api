package contracts.api

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
Save transaction and sign as unapproved that makes total to be exceed the limit

```
given:
	transaction
when:
    saved 
then:
	we'll return information that has unapproved state info and id
```

""")
    request {
        method POST()
        url "/transactions"
        headers {
            contentType applicationJson()
        }
        body("""
			{
				"firstname": "mary",
				"lastname": "doe",
				"email": "mary.doe@gmail.com",
				"productName": "USB Disc",
				"price": 100.0,
				"transactionCode": "TR025"
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
				"id": 1,
				"state": "UNAPPROVED",
			}
		"""
        )
    }
}