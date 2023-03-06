# TRABAJO DE PROMOCIÓN BD2

## **Motivación y objetivos**
---
Cuando nos encontramos frente a pequeñas colecciones de datos, sobre las que debemos realizar consultas, tendemos a elegir el motor de bases de datos con el cual nos sentimos más cómodos. Esta no es la situación cuando contamos con un set de datos de gran tamaño (en el orden de los cientos de miles o millones). En estos casos debemos detenernos y analizar en profundidad la naturaleza de nuestros datos para hallar el motor de búsqueda que mejor se ajuste. 

A su vez, cuando realizamos búsquedas sobre grandes volúmenes de datos, los tiempos de ejecución de las consultas tienden a crecer considerablemente. Tanto es así que debemos optimizar las consultas y seleccionar el motor que maximice la performance. 

El presente trabajo trata acerca de un análisis y comparación entre diversas búsquedas que se realizan sobre un mismo dataset de accidentes que contiene 1 millón de datos. Éstas se efectuan, según la naturaleza de la consulta, sobre distintos motores de bases de datos, incluyendo PostreSQL, MongoDB y Elasticsearch. El trabajo se desarrolla utilizando de una API REST construida sobre Spring Boot (Java) con endpoints que ejecutan las siguientes consultas:

    1. Devolver todos los accidentes ocurridos entre 2 fechas dadas

    2. Determinar las condiciones más comunes en los accidentes (hora del día, condiciones climáticas, etc)

    3. Dado un punto geográfico y un radio (expresado en kilómetros) devolver todos los accidentes ocurridos dentro del radio.

    4. Obtener la distancia promedio desde el inicio al fin del accidente

    5. Devolver los 5 puntos más peligrosos (definiendo un determinado radio y utilizando los datos de los accidentes registrados).

    6. Devolver la distancia promedio que existe entre cada accidente y los 10 más cercanos.

    7. Devolver el nombre de las 5 calles con más accidentes.
 
 En la sección [Endpoints](#Endpoints), se encontran los endpoints que resuelven las consultas junto con una explicación resumida de su funcionamiento y los parámetros que toman.

 Por otro lado, un problema que puede surgir en todo proyecto que requiera del almacenamiento de datos, es decidir qué tipo de base de datos se ajusta mejor al escenario en cuestión. Pueden usarse bases de datos relacionales, no relacionales, orientadas a grafos, etc. En muchas ocaciones no es posible definir esta característica del proyecto en etapas tempranas de desarrollo. Es por ello que mostraremos el uso de los llamados *Repositorios* que nos proporciona el framework Spring Boot, con los cuales conseguiremos una alta abstracción del acceso a las distintas bases de datos. Esto nos permite trabajar sobre los datos y realizar consultas sin darle importancia a la tecnología subyacente que ejecuta nuestras búsquedas. 

## **Instalación**
---
En nuestro grupo decidimos construir un proyecto que sea autocontenido, para evitar la instalación de programas de forma local. Es por ello que se utiliza la herramienta Docker Compose para definir y ejecutar múltiples contenedores de Docker en unos pocos pasos.

Para la ver las instrucciones de instalación del proyecto diríjase al archivo [Installation.md](INSTALLATION.md) ubicado dentro de este mismo directorio.


## **Endpoints**
---

A continuación describiremos los endpoints por medio de los cuales se pueden realizar las consultas HTTP requeridas a las distintas bases de datos:

( ***Todo endpoint debe comenzar con "http://localhost:${SERVER_HOST_PORT}/accidents"*** )


1.  > *Retornar una lista de los accidentes ocurridos entre 2 fechas dadas.*
    > - **Motor de busqueda:** *PostgreSQL*
    >
    > - **Parametros:**
    >   - *start*  - la fecha de comienzo de intervalo
    >   - *end*  - la fecha de fin de intervalo
    >   - *page*  - la pagina solicitada
    >   - *perPage*  - la cantidad de elementos por pagina. (Por defecto son 10)
    > <br></br>
    >```
    >.../between-dates?start=[DD-MM-YYYY]&end=[DD-MM-YYYY]&page=[N]&perPage=[M]
    >```
    > - **Ejemplo:**
    >
    >   - http://localhost:8081/accidents/between-dates?start=08-02-2016&end=09-02-2016&page=2&perPage=10
    > - **Output (ejemplo):**
    > ```json
    >    "paginationInfo": {
    >       "perPage": 10,
    >       "totalElementsInCurrentPage": 10,
    >       "totalPages": 4,
    >       "currentPage": 1,
    >       "totalElements": 36
    >    },
    >    "content": [
    >        {
    >           "id": "A-1",
    >           "source": "MapQuest",
    >           "tmc": "201.0",
    >           "severity": 3,
    >           "startTime": "2016-02-08T08:46:00.000+00:00",
    >           "endTime": "2016-02-08T14:00:00.000+00:00",
    >           ...
    >           "astronomicalTwilight": "Night"
    >        },
    >           ...  
    >   ]             
    >```

<br></br>

2.  > *Retornar lista de accidentes ocurridos a una distancia (radius) en km de un punto geográfico*
    > - **Motor de busqueda:** *MongoDB* o *Elasticsearch*
    > 
    > - **Parametros:**
    >   - *longitude*  - la longitud de la ubicacion del accidente. (Entre -180 y 180)
    >   - *latitude*  - la latitud de la ubicacion del accidente. (Entre 0 y 90)
    >   - *radius*  - el radio de la circunferencia que rodea al accidente. Medido en km.
    ><br></br>

    >**(Version Mongo)**
    >
    >```
    >.../near?longitude=[P]&latitude=[Q]&radius=[N]
    >```
    >**(Version Elastic)**
    >```
    >.../near2?longitude=[P]&latitude=[Q]&radius=[N]
    >```
    > - Ejemplos:
    >
    >   - http://localhost:8081/accidents/near?longitude=-84.058723&latitude=39.865147&radius=10
    >
    >   - http://localhost:8081/accidents/near2?longitude=-84.058723&latitude=39.865147&radius=10
    >
    > - **Output (ejemplo):**
    > ```json
    >    "paginationInfo": {
    >       "perPage": 10,
    >       "totalElementsInCurrentPage": 10,
    >       "totalPages": 4,
    >       "currentPage": 1,
    >       "totalElements": 36
    >    },
    >    "content": [
    >        {
    >           "id": "A-1",
    >           "source": "MapQuest",
    >           "tmc": "201.0",
    >           "severity": 3,
    >           "startTime": "2016-02-08T08:46:00.000+00:00",
    >           "endTime": "2016-02-08T14:00:00.000+00:00",
    >           ...
    >           "astronomicalTwilight": "Night"
    >        },
    >           ...  
    >   ]             
    >```

<br></br>

3.  > *Retornar la distancia promedio (en metros) desde el inicio al fin del accidente*
    > - **Motor de busqueda:** *PostgreSQL*
    ><br></br>
    >```
    >.../average-distance
    >```
    >- Ejemplo:
    >
    >   - http://localhost:8081/accidents/average-distance
    >
    > - **Output (ejemplo):**
    > ```json
    >    {
    >       "averageDistance": 146.37715431472577
    >    }
    >```

<br></br>

4.  > *Retorna las 5 calles con más accidentes*
    > - **Motor de busqueda:** *PostgreSQL*
    ><br></br>
    >```
    >.../dangerous-streets
    >```
    > - Ejemplo:
    >
    >   - http://localhost:8081/accidents/dangerous-streets
    >
    > - **Output (ejemplo):**
    > ```json
    >    [
    >       {
    >           "street": "I-75 S",
    >           "totalAccidents": 7
    >       },
    >           ...  
    >       {
    >           "street": "N High St",
    >           "totalAccidents": 4
    >       },
    >    ]             
    >```

<br></br> 


5.  > *Retornar la distancia promedio (en km) de cada accidente a los 10 más cercanos*
    > - **Motor de busqueda:** *MongoDB* o *Elasticsearch*
    >
    > - **Funcionamiento:**
    >
    >   En primer lugar, por cada ubicacion de los accidentes, se filtran los que se encuentran a maximo 5km del accidente. Luego se calcula la distancia de estos al punto original y se promedian dichas distancias.
    >
    > - **Parametros:**
    >   - *page*  - la pagina solicitada
    >   - *perPage*  - la cantidad de elementos por 
    ><br></br>

    >**(Version Mongo)**
    >
    >```
    >.../near/average-distance?page=[N]&perPage=[M]
    >```
    >**(Version Elastic)**
    >```
    >.../near/average-distance2?page=[N]&perPage=[M]
    >```
    > - Ejemplos:
    >
    >   - http://localhost:8081/accidents/near/average-distance?page=1&perPage=10
    >
    >   - http://localhost:8081/accidents/near/average-distance2?page=1&perPage=10
    >
    > - **Output (ejemplo):**
    > ```json
    >    "paginationInfo": {
    >       "perPage": 10,
    >       "totalElementsInCurrentPage": 10,
    >       "totalPages": 4,
    >       "currentPage": 1,
    >       "totalElements": 36
    >    },
    >    "content": [
    >        {
    >            "averageDistance": 4.992713397617309,
    >            "id": "A-1"
    >        },
    >        {
    >            "averageDistance": 2.9515865565346373,
    >            "id": "A-2"
    >        },
    >        ...
    >   ]             
    >```
    <br></br>

6.  > *Retornar las condiciones más comunes en los accidentes*
    > - **Motor de busqueda:** *PostgreSQL*
    ><br></br>
    >```
    >.../getCommonConditions
    >```
    > - Ejemplo:
    >
    >   - http://localhost:8081/accidents/getCommonConditions
    >
    > - **Output (ejemplo):**
    > ```json
    >    {
    >       "commonAccidentWeatherCondition": "Light Snow",
    >       "commonAccidentWindDirection": "NW",
    >       "commonAccidentHumidity": 100.0,
    >       "commonAccidentStartHour": 7,
    >       "commonAccidentVisibility": 10.0
    >    }           
    >```

<br></br>

7.  > *Retornar los puntos más peligrosos a partir de un determinado radio.*
    > - **Motor de busqueda:** *MongoDB* 
    > - **Funcionamiento:**
    >   
    >   Primeramente se agrupan los accidentes por ubicacion, contando la cantidad de accidentes que ocurren en un mismo sitio. Luego, por cada uno de estos puntos geográficos se filtran los que estan a una cierta distancia (radio) y se suman sus severidades. Esta suma final determina el campo por el cual se ordenáran los accidentes.
    >
    > - **Parametros:**
    >   - *radius*  - el radio de la circunferencia
    >   - *amount*  - la cantidad de puntos a obtener
    > <br></br>
    >```
    >.../getMostDangerousPoints?radius=[N]&amount=[M]
    >```
    > - **Ejemplo:**
    >
    >     - http://localhost:8081/accidents/getMostDangerousPoints?radius=2&amount=5 
    >
    > - **Output (ejemplo)**
    > ```json
    >     [{
    >        "point": {
    >           "x": -84.205582,
    >           "y": 39.747753
    >        },
    >        "totalSeverity": 36,
    >        "totalAccidentsInLocation": 3,
    >        "totalNearAccidents": 13
    >     },
    >       ...
    >     {
    >        "point": {
    >            "x": -83.016663,
    >            "y": 40.11195
    >        },
    >        "totalSeverity": 14,
    >        "totalAccidentsInLocation": 4,
    >        "totalNearAccidents": 1
    >     }]
    >```

<br></br>

## **Conclusiones**
---
