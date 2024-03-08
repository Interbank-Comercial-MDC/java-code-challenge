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
    Transaction -- 1) Guarda operación con estado pendiente --> transactionDatabase[(Database)]
    Transaction -- 2) Envía evento de registro de operación --> Anti-Fraud
    Anti-Fraud -- 3.a) Envía evento de cambio de estado de operación a aprobado --> Transaction
    Anti-Fraud -- 3.b) Envía evento de cambio de estado de operación a rechazado --> Transaction
    Transaction -- 4) Actualiza operación con el estado recibido por Anti-Fraud --> transactionDatabase[(Database)]
```

# Stack Tecnológico a utilizar

<ol>
  <li>Java - Spring Framework - Spring Boot</li>
  <li>BD Oracle</li>
  <li>Gestor de colas Kafka</li>    
</ol>

Nosotros estamos brindandote un `Dockerfile` para que rápidamente puedas configurar tu entorno de desarrollo.

Deberías tener 2 recursos (a nivel de API):

1. Recurso para crear una operación que contenga la siguiente estructura:

```json
{
  "accountExternalIdDebit": "Guid",
  "accountExternalIdCredit": "Guid",
  "tranferTypeId": 1,
  "value": 120
}
```

2. Recurso que recupera una operación

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

Puede utilizar cualquier método para almacenar datos de transacciones, pero debe considerar que podemos lidiar con escenarios de gran volumen en los que tenemos una gran cantidad de escrituras y lecturas de los mismos datos al mismo tiempo. ¿Cómo abordaría este requisito?

# Envía tu Resolución

Cuando termines tu resolución, después de realizar el fork al repositorio, tú **must** abrir una solicitud de extracción a nuestro repositorio. No hay limitaciones para la implementación, puede seguir el paradigma de programación, la modularización y el estilo que creas que es la solución más adecuada.

Si tienes alguna duda, por favor ponte en contacto con nosotros.
