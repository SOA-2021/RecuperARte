# Modelo de los datos a persistir

En vez de modelar directamente los datos que se muestran en las estadisticas,
vamos a guardar datos mas simples, que luego mediante su union podemos llevar a
estadisticas.

Podemos hacer que cada 1 o 2 segundos que se mueve el usuario, guardar los
siguientes datos.

```
# run_data
| meters | is_running | datetime                 |
| 0.5    | true       | 2021-06-11T22:00:00.0000 |
| 0.5    | true       | 2021-06-11T22:00:01.0000 |
| 0.6    | true       | 2021-06-11T22:00:02.0000 |
| 0.5    | true       | 2021-06-11T22:00:04.0000 |
| 0.7    | true       | 2021-06-11T22:00:06.0000 |
| 0.5    | true       | 2021-06-11T22:00:08.0000 |
| 0.1    | false      | 2021-06-12T22:30:00.0000 |
| 0.2    | false      | 2021-06-12T22:30:01.0000 |
| 0.1    | false      | 2021-06-12T22:30:02.0000 |
| 0.3    | false      | 2021-06-12T22:30:03.0000 |
| 0.1    | false      | 2021-06-12T22:30:04.0000 |
```

De esta manera, podemos obtener las dos estadisticas utilizando dos queries
distintas.

## Metros y tiempo

Esta query

``` sql
SELECT datetime, sum(meters), sum(datetime)
FROM run_data
GROUP BY date(datetime)
```

Permite obtener los datos siguientes

```
| Dia        | Metros | Tiempo     |
| 2021-06-12 | 0.8mt  | 4 segundos |
```

## A que hora sale a correr y que dias

Esta query

``` sql
SELECT datetime
FROM run_data
WHERE is_running = TRUE
GROUP BY FLOOR(MOD(datetime)/3600) -- agrupo por hora
```

Luego procesado en Java:

``` java
/* ... */
List<LocalDateTime> horarios = DAO.getMetricaHorarios();
for ( LocalDateTime dt: horarios ) {
	DayOfWeek day = dt.getDayOfWeek();
	LocalTime time = dt.toLocalTime();
	
	addCellToTable(day, time);
}
/* ... */
```

Permite obtener

```
| Dia de la semana | Horario |
| Domingo          | 20:00   |
```
