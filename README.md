# Money Transfer

Money transfer application is designed to transfer the money from one account to another account.

## Money Transfer Service API
```http://localhost:8080/api/transaction/transfer```

#### Request Sample 
```
{
  "fromAccount": {
  	"accountNumber":"ACC-1234567",
  	"accountHolderName":"Pradip Rimal"
  },
  "toAccount": {
  	"accountNumber":"ACC-1234002",
  	"accountHolderName":"Dan Rj"
  },
  "amount":100,
  "remarks":"Wallet Paymnet"
}
```

#### Response in case of success 
```
{
    "code": 200,
    "data": "Successfully transferred"
}
```

#### Response in case of invalid request
```
 {
       "code": 406,
       "message": "Insufficient amount found"
 }
   ```


### Technology Stack
 - Java 1.8
    - Dropwizard 1.3.12
    - JDBI 1.3.12
 - Maven 3.5.4

### Persistence
 - h2database 1.4.199

## Development

### Setting up developer environment
 1. Code checkout
 2. Configuring IDE

### Build

> Requires JDK 1.8 and Maven.

Using locally installed Maven.

```bash
mvn clean install
```

### Running Application
```shell
java -jar server config.yml

Note: Provide location of config.yml file.
example: java -jar money-transfer-1.0-SNAPSHOT.jar server ../config.yml(From the target directory)

```