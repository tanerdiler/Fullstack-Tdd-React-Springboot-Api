```
PurchasingAgent agent = PurchasingAgent.firstname("Taner").lastname("Diler).email("taner.diler@gmail.com").get();
boolean accepted = agent.save(transaction);
```


java -jar target/demo-stub-runner-0.0.1-SNAPSHOT.jar --server.port=8070 --stubrunner.ids=jugistanbul.tdd:transactions-port-rest:0.0.1-SNAPSHOT:stubs:8000


