Para correr el servicio:
	
$ docker build . -t challenge-back-end
$ docker run -p 8081:8081 challenge-back-end


Como consultarlo por API:
[GET] localhost:8081/ip/:ipNumber
Obtiene toda la informacion asociada a una ip.
Ejemplo de respuesta:
{
"country_name": "United Kingdom of Great Britain and Northern Ireland",
"iso_country_code": "GBR",
"official_languages": [
"English"
],
"current_hours": [
"2019-12-09T02:07:44.095Z",
"2019-12-09T02:07:44.112Z",
"2019-12-09T02:07:44.112Z",
"2019-12-09T02:07:44.112Z",
"2019-12-09T02:07:44.112Z",
"2019-12-09T02:07:44.112Z",
"2019-12-09T02:07:44.112Z",
"2019-12-09T02:07:44.112Z",
"2019-12-09T02:07:44.112Z"
],
"estimated_distance": 7609.181870219288,
"currencies": [
{
"local_currency": "British pound",
"euro_quote": 0.841078
}
]
}

[GET]/distances
Obtiene informacion asociada a distancias consultadas con anterioridad
Ejemplo de respuesta:
{
"furthest_distance_from_bs_as": {
"distance": 7609.181870219288,
"country": "United Kingdom of Great Britain and Northern Ireland"
},
"average_distance_from_bs_as": 7609.181870219288,
"nearest_distance_from_bs_as": {
"distance": 7609.181870219288,
"country": "United Kingdom of Great Britain and Northern Ireland"
}
}

Decisiones tomadas:
Se tomo el euro como base para convertir cambios de moneda
dado que el servicio fixer posee esta moneda como default y si se quiere consultar
con base en dolares se debe pagar otro plan

Las distancias son guardadas en un repositorio con el uso de un bloque sincronico
para guardar nueva informacion. Esto se hace en memoria , es decir que si se 
apaga la aplicacion y se vuelve a prender ya no estara la informacion vieja.


Para el unico client que se construyo un facade fue para fixer,
dado el formato de respuesta que daba en formato de mapa , para facilitar 
el acceso al dato de la cotizacion al service.
Para los demas servicios no se considero necesario



