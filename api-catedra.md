# API

Hay dos ambientes de API, que se especifican con un parametro en cada request:

1. TEST: entorno que permite hacer pruebas. No permite realizar LOGIN.
2. PROD: entorno de produccion. Permite todas las consultas. Es el entorno en el
	 que debera ser entregada la APP.

## Registro de usuario

Request.

```
POST http://so-unlam.net.ar/api/api/register
{
	"env": "TEST" | "PROD",
	"name": string,
	"lastname": string,
	"dni": number,
	"email": string,
	"password": string,
	"commission": 3900,
	"group": number
}
```

Response.

```
200
{
	"success": boolean,
	"env": "TEST" | "PROD",
	"token": string,
	"token_refresh": string
}

400
{
	"success": boolean,
	"env": string,
	"msg": string
}
```

API de Java propuesta.

``` java
public Token registerUser(User newUser) throws ApiException;

Token {
	token, token_refresh: string;
}

User {
	name, lastname, dni, email: string;
}
```

## Login

Request.

```
POST http://so-unlam.net.ar/api/api/login
{
	"email": string,
	"password": string
}
```

Response.

```
200
{
	"success": boolean,
	"token": string,
	"token_refresh": string
}

400
{
	"success": boolean,
	"msg": string
}
```

API de Java propuesta.

``` java
public Token login(String email, String password) throws ApiException;
```

## Refresh Token

Request.

```
PUT http://so-unlam.net.ar/api/api/refresh
Athorization: Bearer <<token_refresh>>
```

Response.

```
200
{
	"success": boolean,
	"token": string,
	"token_refresh": string
}

400
{
	"success": boolean,
	"msg": string
}
```

API de Java propuesta.

``` java
public Token renewToken(Token t) throws ApiException;
```

## Registrar evento

Request.

```
POST http://so-unlam.net.ar/api/api/event
Authorization: Bearer <<token>>
{
	"env": "TEST" | "PROD",
	"type_events": string,
	"description": string
}
```

Response.

```
201 -- solo Prod
{
	"success": boolean,
	"env": "TEST" | "PROD",
	"event": {
		"type_events": string,
		"dni": number,
		"description": string,
		"id": number
	}
}

200 -- solo Test
{
	"success": boolean,
	"env": "TEST" | "PROD",
	"event": {
		"type_events": string,
		"description": string,
	}
}

400
{
	"success": boolean,
	"env": "TEST" | "PROD",
	"msg": string
}
```

API de Java propuesta.

``` java
public void register(Registrable r) throws ApiException;

interface Registrable {
	public EventType type();
	public String description();
}
```

Registrables seran los logins, logouts, y actividad de los dos sensores. Se
utiliza una interfaz.
