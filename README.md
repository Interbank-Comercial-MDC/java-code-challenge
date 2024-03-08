# Reto de programación en Java :rocket:

El reto de código que hemos elaborado nos permitirá conocer tus poderosas habilidades de codificación :smile:. 

Ten en cuenta que la forma correcta de enviar tu resolución es a través de un PR :wink: ... ¡Que te diviertas!

- [Problema](#problem)
- [Stack Tecnológico a utilizar](#tech_stack)
- [Envía tu Resolución](#send_us_your_challenge)

# Problema

Every time a financial transaction is created it must be validated by our anti-fraud microservice and then the same service sends a message back to update the transaction status.
For now, we have only three transaction statuses:

<ol>
  <li>pendiente</li>
  <li>aprobado</li>
  <li>rechazado</li>  
</ol>

Every transaction with a value greater than 1000 should be rejected.

```mermaid
  flowchart LR
    Transaction -- 1. Guarda Operación con estado pendiente --> transactionDatabase[(Database)]
    Transaction -- 2. Envía evento de Operación --> Anti-Fraud
    Anti-Fraud -- 3. (a) Envía Operación con estado aprobado --> Transaction
    Anti-Fraud -- 3. (b) Envía Operación con estado rechazado --> Transaction
    Transaction -- 4. Actualiza estado de Operación -> transactionDatabase[(Database)]
```

# Stack Tecnológico a utilizar

<ol>
  <li>Java - Spring Framework - Spring Boot</li>
  <li>BD Oracle</li>
  <li>Gestor de colas Kafka</li>    
</ol>

Nosotros estamos brindandote un `Dockerfile` para que rápidamente puedas configurar tu entorno de desarrollo.

You must have two resources:

1. Resource to create a transaction that must containt:

```json
{
  "accountExternalIdDebit": "Guid",
  "accountExternalIdCredit": "Guid",
  "tranferTypeId": 1,
  "value": 120
}
```

2. Resource to retrieve a transaction

```json
{
  "transactionExternalId": "Guid",
  "transactionType": {
    "name": ""
  },
  "transactionStatus": {
    "name": ""
  },
  "value": 120,
  "createdAt": "Date"
}
```

## Opcional

You can use any approach to store transaction data but you should consider that we may deal with high volume scenarios where we have a huge amount of writes and reads for the same data at the same time. How would you tackle this requirement?

You can use Graphql;

# Envía tu Resolución

When you finish your challenge, after forking a repository, you **must** open a pull request to our repository. There are no limitations to the implementation, you can follow the programming paradigm, modularization, and style that you feel is the most appropriate solution.

If you have any questions, please let us know.
