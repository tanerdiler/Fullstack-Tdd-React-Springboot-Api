package contracts.api

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
  Return all transactions

  given:
    search criteria
  when:
      search 
  then:
    we'll return all transactions ever saved

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
                          {"id":1,"agent":"john doe","code":"TR123","state":"APPROVED","productName":"USB Disc","price":100.0},
                          {"id":2,"agent":"john doe","code":"TR124","state":"UNAPPROVED","productName":"Flash Disc","price":200.0},
                          {"id":3,"agent":"mary doe","code":"TR125","state":"APPROVED","productName":"Hard Disc","price":300.0},
                          {"id":4,"agent":"mary doe","code":"TR126","state":"UNAPPROVED","productName":"Mac Book Pro","price":400.0}
                      ]
                  }
      """
        )
    }
}