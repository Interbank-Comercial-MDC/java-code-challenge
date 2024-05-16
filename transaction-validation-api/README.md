# Transaction Validation API

Este proyecto es una API para la validación de transacciones financieras, desarrollada en Spring Boot. La API permite crear, obtener y buscar transacciones a través de varios endpoints.

## Endpoints

### Crear una transacción

**URL:** `/api/transactions/create`

**Método HTTP:** `POST`

**Descripción:** Crea una nueva transacción en el sistema.

**Request Body:**

- `TransactionRequest` (JSON): Objeto que representa la transacción a ser creada.

**Response:**

- `200 OK`: Transacción creada exitosamente.
- `Transaction` (JSON): Objeto de la transacción creada.

**Ejemplo:**

```json
{
    "id": 1,
    "accountExternalIdDebit": "3fa85f64-5717-4562-b3fc-5a463f22afa6",
    "accountExternalIdCredit": "3fa29f64-5717-4562-b3fc-2b963f11afa6",
    "tranType": "Agente",
    "value": 1299.0,
    "status": "Pendiente",
    "createdAt": "16-05-2024 22:58:14"
}
```

### Obtener todas las transacciones

**URL:** `/api/transactions`

**Método HTTP:** `GET`

**Descripción:** Obtiene una lista de todas las transacciones registradas en el sistema.

**Response:**

- `200 OK`: Lista de todas las transacciones.
- `List<Transaction>` (JSON): Lista de objetos de transacciones.

**Ejemplo de Respuesta:**

```json
[
    {
        "id": 1,
        "accountExternalIdDebit": "3fa85f64-3267-1234-b3fc-5a463f22afa6",
        "accountExternalIdCredit": "3fa29f64-4698-3623-b3fc-2b963f11afa6",
        "tranType": "Agente",
        "value": 399.0,
        "status": "Aprobado",
        "createdAt": "16-05-2024 23:06:32"
    },
    {
        "id": 2,
        "accountExternalIdDebit": "3fa85f64-3267-4562-b3fc-5a463f22afa6",
        "accountExternalIdCredit": "3fa29f64-4698-4562-b3fc-2b963f11afa6",
        "tranType": "Agente",
        "value": 1299.0,
        "status": "Rechazado",
        "createdAt": "16-05-2024 23:06:44"
    }
]
```

### Buscar transacciones por ID externo de cuenta

**URL:** `/api/transactions/search`

**Método HTTP:** `GET`

**Descripción:** Busca transacciones utilizando el ID externo de la cuenta.

**Parámetro de URL:**

- `accountExternalId` (UUID): ID externo de la cuenta para buscar transacciones.

**Response:**

- `200 OK`: Lista de transacciones asociadas con el ID externo de la cuenta.
- `List<TransactionResponse>` (JSON): Lista de respuestas de transacciones.

**Ejemplo de Respuesta:**

```json
[
    {
        "transactionExternalId": "3fa29f64-5717-4562-b3fc-2b963f11afa6",
        "transactionType": {
            "name": "Agente"
        },
        "transactionStatus": {
            "name": "Pendiente"
        },
        "value": 1299.0,
        "createdAt": "16-05-2024 22:58:14"
    }
]
```