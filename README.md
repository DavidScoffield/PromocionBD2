# Bases de Datos 2 - Trabajo de promoción

Integrantes del grupo:

- Giorgetti Valentín (17133/2)
- Scoffield David (17282/5)
- Suelgaray Franco (17274/5)

---

## **Índice**
- [**1. Motivación y objetivos**](#1-motivación-y-objetivos)
- [**2. Instalación**](#2-instalación)
- [**3. Endpoints**](#3-endpoints)
    - [1. *Devolver todos los accidentes ocurridos entre 2 fechas dadas*](#1-devolver-todos-los-accidentes-ocurridos-entre-2-fechas-dadas)
    - [2. *Determinar las condiciones más comunes en los accidentes (hora del día, condiciones climáticas, etc)*](#2-determinar-las-condiciones-más-comunes-en-los-accidentes-hora-del-día-condiciones-climáticas-etc)
    - [3. *Dado un punto geográfico y un radio (expresado en kilómetros) devolver todos los accidentes ocurridos dentro del radio*](#3-dado-un-punto-geográfico-y-un-radio-expresado-en-kilómetros-devolver-todos-los-accidentes-ocurridos-dentro-del-radio)
    - [4. *Obtener la distancia promedio desde el inicio al fin del accidente*](#4-obtener-la-distancia-promedio-desde-el-inicio-al-fin-del-accidente)
    - [5. *Devolver los 5 puntos más peligrosos (definiendo un determinado radio y utilizando los datos de los accidentes registrados)*](#5-devolver-los-5-puntos-más-peligrosos-definiendo-un-determinado-radio-y-utilizando-los-datos-de-los-accidentes-registrados)
    - [6. *Devolver la distancia promedio que existe entre cada accidente y los 10 más cercanos*](#6-devolver-la-distancia-promedio-que-existe-entre-cada-accidente-y-los-10-más-cercanos)
    - [7. *Devolver el nombre de las 5 calles con más accidentes*](#7-devolver-el-nombre-de-las-5-calles-con-más-accidentes)
- [**4. Comparación MongoDB vs ElasticSearch**](#4-comparación-mongodb-vs-elasticsearch)
    - [Comparación de la consulta 3](#comparación-de-la-consulta-3)
    - [Comparación de la consulta 6](#comparación-de-la-consulta-6)
- [**5. Conclusiones**](#5-conclusiones)

---

## **1. Motivación y objetivos**

Cuando nos encontramos frente a pequeñas colecciones de datos, sobre las que debemos realizar consultas, tendemos a elegir el motor de bases de datos con el cual nos sentimos más cómodos. Esta no es la situación cuando contamos con un set de datos de gran tamaño (en el orden de los cientos de miles o millones). En estos casos debemos detenernos y analizar en profundidad la naturaleza de nuestros datos para hallar el motor de búsqueda que mejor se ajuste. 

A su vez, cuando realizamos búsquedas sobre grandes volúmenes de datos, los tiempos de ejecución de las consultas tienden a crecer considerablemente. Tanto es así que debemos optimizar las consultas y seleccionar el motor que maximice la performance. 

El presente trabajo trata acerca de un análisis y comparación entre diversas búsquedas que se realizan sobre un mismo dataset de accidentes que contiene 3 millones de datos. Éstas se efectúan, según la naturaleza de la consulta, sobre distintos motores de bases de datos, incluyendo PostgreSQL, MongoDB y Elasticsearch. El trabajo se desarrolla utilizando una API REST construida sobre Spring Boot (Java) con endpoints que ejecutan las siguientes consultas:

    1. Devolver todos los accidentes ocurridos entre 2 fechas dadas

    2. Determinar las condiciones más comunes en los accidentes (hora del día, condiciones climáticas, etc)

    3. Dado un punto geográfico y un radio (expresado en kilómetros) devolver todos los accidentes ocurridos dentro del radio

    4. Obtener la distancia promedio desde el inicio al fin del accidente

    5. Devolver los 5 puntos más peligrosos (definiendo un determinado radio y utilizando los datos de los accidentes registrados)

    6. Devolver la distancia promedio que existe entre cada accidente y los 10 más cercanos

    7. Devolver el nombre de las 5 calles con más accidentes
 
 En la sección [Endpoints](#3-endpoints), se encuentran los endpoints que resuelven las consultas junto con una explicación resumida de su funcionamiento y los parámetros que toman.

 Por otro lado, un problema que puede surgir en todo proyecto que requiera del almacenamiento de datos, es decidir qué tipo de base de datos se ajusta mejor al escenario en cuestión. Pueden usarse bases de datos relacionales, no relacionales, orientadas a grafos, etc. En muchas ocasiones no es posible definir esta característica del proyecto en etapas tempranas de desarrollo. Es por ello que mostraremos el uso de los llamados *Repositorios* que nos proporciona el framework Spring Boot, con los cuales conseguiremos una alta abstracción del acceso a las distintas bases de datos. Esto nos permite trabajar sobre los datos y realizar consultas sin darle importancia a la tecnología subyacente que ejecuta nuestras búsquedas. 

---

## **2. Instalación**

En nuestro grupo decidimos construir un proyecto que sea autocontenido, para evitar la instalación de software de forma local. Es por ello que se utiliza la herramienta Docker Compose para definir y ejecutar múltiples contenedores de Docker en unos pocos pasos.

Para ver las instrucciones de instalación del proyecto diríjase al archivo [INSTALLATION.md](INSTALLATION.md) ubicado dentro de este mismo directorio.

---

## **3. Endpoints**

A continuación describiremos los endpoints por medio de los cuales se pueden realizar las consultas HTTP requeridas a las distintas bases de datos:

> ***Todo endpoint debe comenzar con "http://localhost:${SERVER_HOST_PORT}/accidents"***. En los ejemplos utilizaremos SERVER_HOST_PORT=8081

### 1. *Devolver todos los accidentes ocurridos entre 2 fechas dadas*

- **Motor de búsqueda:** *PostgreSQL*

- **Nombre del endpoint:** */between-dates*

- **Parámetros:**

  - *start*: la fecha de comienzo de intervalo en formato DD-MM-YYYY
  - *end*: la fecha de fin de intervalo en formato DD-MM-YYYY
  - *page*: la pagina solicitada (por defecto 1)
  - *perPage*: la cantidad de elementos por pagina (por defecto 10)
    
- **Ejemplo:**
    
    *GET http://localhost:8081/accidents/between-dates?start=08-02-2016&end=09-02-2016&page=1&perPage=10*
    
    *Output (ejemplo):*
    
    ```json
        "paginationInfo": {
           "perPage": 10,
           "totalElementsInCurrentPage": 10,
           "totalPages": 4,
           "currentPage": 1,
           "totalElements": 36
        },
        "content": [
            {
               "id": "A-1",
               "source": "MapQuest",
               "tmc": "201.0",
               "severity": 3,
               "startTime": "2016-02-08T08:46:00.000+00:00",
               "endTime": "2016-02-08T14:00:00.000+00:00",
               ...
               "astronomicalTwilight": "Night"
            },
               ...
       ]             
    ```

### 2. *Determinar las condiciones más comunes en los accidentes (hora del día, condiciones climáticas, etc)*

- **Motor de búsqueda:** *PostgreSQL*

- **Nombre del endpoint:** */getCommonConditions*

- **Parámetros:**

  - No posee parámetros

- **Ejemplo:**

    *GET http://localhost:8081/accidents/getCommonConditions*

    *Output (ejemplo):*
    
    ```json
        {
           "commonAccidentWeatherCondition": "Light Snow",
           "commonAccidentWindDirection": "NW",
           "commonAccidentHumidity": 100.0,
           "commonAccidentStartHour": 7,
           "commonAccidentVisibility": 10.0
        }           
    ```

### 3. *Dado un punto geográfico y un radio (expresado en kilómetros) devolver todos los accidentes ocurridos dentro del radio*

- **Motor de búsqueda:** *MongoDB* o *Elasticsearch*

- **Nombre del endpoint:** 

    - */near* (MongoDB) 
    - */near2* (Elasticsearch)

- **Parámetros:**

  - *page*: la pagina solicitada (por defecto 1)
  - *perPage*: la cantidad de elementos por pagina (por defecto 10)
  - *longitude*: la longitud de la ubicacion del accidente (entre -180 y 180)
  - *latitude*: la latitud de la ubicacion del accidente (entre 0 y 90)
  - *radius*: el radio de búsqueda (en km)

- **Ejemplo:**
    
    *GET http://localhost:8081/accidents/near?longitude=-84.058723&latitude=39.865147&radius=10* (MongoDB)

    *GET http://localhost:8081/accidents/near2?longitude=-84.058723&latitude=39.865147&radius=10* (Elasticsearch)
    
    *Output (ejemplo):*
    
    ```json
        "paginationInfo": {
           "perPage": 10,
           "totalElementsInCurrentPage": 10,
           "totalPages": 4,
           "currentPage": 1,
           "totalElements": 36
        },
        "content": [
            {
               "id": "A-1",
               "source": "MapQuest",
               "tmc": "201.0",
               "severity": 3,
               "startTime": "2016-02-08T08:46:00.000+00:00",
               "endTime": "2016-02-08T14:00:00.000+00:00",
               ...
               "astronomicalTwilight": "Night"
            },
               ...
       ]             
    ```

### 4. *Obtener la distancia promedio desde el inicio al fin del accidente*

- **Motor de búsqueda:** *PostgreSQL*

- **Nombre del endpoint:** */average-distance*

- **Parámetros:**

  - No posee parámetros

- **Ejemplo:**

    *GET http://localhost:8081/accidents/average-distance*

    *Output (ejemplo):*
    
    ```json
        {
           "averageDistance": 146.37715431472577
        }
    ```

### 5. *Devolver los 5 puntos más peligrosos (definiendo un determinado radio y utilizando los datos de los accidentes registrados)*

> En primer lugar se buscan los puntos geográficos donde más accidentes hayan ocurrido. Luego, por cada punto, se buscan los accidentes que se encuentren dentro del radio especificado, y se suman sus severidades. El resultado estará ordenado por la severidad total del punto, dada por la suma mencionada anteriormente.

- **Motor de búsqueda:** *MongoDB*

- **Nombre del endpoint:** */getMostDangerousPoints*

- **Parámetros:**

  - *radius*: el radio de la circunferencia (en km)
  - *amount*: la cantidad de puntos a obtener (por defecto 5)

- **Ejemplo:**
    
    *GET http://localhost:8081/accidents/getMostDangerousPoints?radius=2&amount=5*
    
    *Output (ejemplo):*
    
    ```json
        [
           {
              "point": {
                 "x": -84.205582,
                 "y": 39.747753
              },
              "totalSeverity": 36,
              "totalAccidentsInLocation": 3,
              "totalNearAccidents": 13
           },
           ...
           {
              "point": {
                 "x": -83.016663,
                 "y": 40.11195
              },
              "totalSeverity": 14,
              "totalAccidentsInLocation": 4,
              "totalNearAccidents": 1
           }
        ]
    ```

### 6. *Devolver la distancia promedio que existe entre cada accidente y los 10 más cercanos*

> Por cada accidente se buscan los 10 más cercanos en un radio de 5km, luego se calcula la distancia entre estos y el accidente original, y finalmente se promedian dichas distancias.

- **Motor de búsqueda:** *MongoDB* o *Elasticsearch*

- **Nombre del endpoint:** 

    - */near/average-distance* (MongoDB) 
    - */near/average-distance2* (Elasticsearch)

- **Parámetros:**

  - *page*: la pagina solicitada (por defecto 1)
  - *perPage*: la cantidad de elementos por pagina (por defecto 10)

- **Ejemplo:**
    
    *GET http://localhost:8081/accidents/near/average-distance?page=1&perPage=10* (MongoDB)

    *GET http://localhost:8081/accidents/near/average-distance2?page=1&perPage=10* (Elasticsearch)
    
    *Output (ejemplo):*

     ```json
        "paginationInfo": {
           "perPage": 10,
           "totalElementsInCurrentPage": 10,
           "totalPages": 4,
           "currentPage": 1,
           "totalElements": 36
        },
        "content": [
            {
                "averageDistance": 4.992713397617309,
                "id": "A-1"
            },
            {
                "averageDistance": 2.9515865565346373,
                "id": "A-2"
            },
            ...
       ]             
    ```

### 7. *Devolver el nombre de las 5 calles con más accidentes*

- **Motor de búsqueda:** *PostgreSQL*

- **Nombre del endpoint:** */dangerous-streets*

- **Parámetros:**

  - No posee parámetros

- **Ejemplo:**

    *GET http://localhost:8081/accidents/dangerous-streets*

    *Output (ejemplo):*
    
     ```json
        [
           {
               "street": "I-75 S",
               "totalAccidents": 7
           },
               ...  
           {
               "street": "N High St",
               "totalAccidents": 4
           },
        ]             
    ```

---

## **4. Comparación MongoDB vs ElasticSearch**

Dos de las consultas fueron resueltas y testeadas utilizando tanto MongoDB como Elasticsearch. Ambos DBMS cuentan con un tipo de dato primitivo que permite modelar puntos geográficos con facilidad. Es por ello que buscamos una comparación de performance sobre consultas que involucraron la locación geográfica de los accidentes.

A continuación, presentamos el análisis de dos consultas que se efectuaron sobre un subconjunto de 10.000 accidentes.

> Realizamos el análisis sobre un subconjunto de sólo 10.000 accidentes debido al límite que nos presenta Elasticsearch al paginar los resultados. El parámetro que controla esta propiedad es *"max_result_window"* y está seteado a 10.000.

> A pesar de ello, decidimos hacer la comparación entre los motores de bases de datos para obtener una vista general de cómo se comportan sobre una colección de datos pequeña. Probablemente estos valores no se repliquen sobre conjuntos mucho más grandes de datos, sino que podrían fluctuar luego de cierto umbral, o hasta podría ser Elasticsearch el motor más apto para resolver consultas grandes.

### Comparación de la consulta 3

> *Dado un punto geográfico y un radio (expresado en kilómetros) devolver todos los accidentes ocurridos dentro del radio*

En esta comparación utilizamos distintos valores de radio para calcular las distancias al accidente.

El punto sobre el cual se realizó la consulta estaba localizado en (-84.058723, 39.865147), donde la primera coordenada es la longitud y la segunda la latitud del punto geográfico. Por otro lado, se utilizó una paginación donde cada página tenía 1000 elementos.

Finalmente, los resultados fueron medidos en milisegundos:

![Mongo vs Elastic](/images/MongoVsElastic-C2.png)

La imagen nos muestra que, a medida que aumentamos el radio, el rendimiento de Elasticsearch disminuye, mientras que MongoDB mantiene una performance "estable", obteniendo resultados hasta 5 veces mejores que su rival.

### Comparación de la consulta 6 
  
> *Retornar la distancia promedio que existe entre cada accidente y los 10 más cercanos.*

Para realizar este análisis, resolvimos repetidamente las consultas con distintos parámetros, con el fin de conseguir generalidad. Estos fueron: 
- Número de página
- Cantidad de elementos por página

Cabe aclarar que se evitó repetir la misma consulta de forma sucesiva con un mismo DBMS para que los resultados no se vieran tan afectados por el caching de memoria que tienen estos motores.

Los siguientes son los resultados obtenidos, medidos en ms (milisegundos), para cada motor de búsqueda y cada variación de los parámetros:

![Mongo vs Elastic](/images/MongoVsElastic-C6.jpg)

Podemos apreciar que ambos DBMS tienden a reducir su tiempo de ejecución a medida que se avanza en las páginas. Este efecto puede atribuirse al cacheo de los datos en memoria.

Por otra parte, destacamos que cuanto mayor es el tamaño de la página, peor resulta el rendimiento de Elasticsearch. MongoDB, por su parte, obtiene mejores resultados para tamaños de página mayores o iguales a 100.

---

## **5. Conclusiones**

La naturaleza de los datos que conforman nuestro dataset nos fuerza a tomar una decisión sobre el tipo de base de datos a utilizar para almacenarlos. Una colección de puntos geográficos no serán tratados de la misma forma que datos sobre clientes de un banco. Los datos poseen, en general, una estructura subyacente que no es sencilla de apreciar a simple vista. 

La colección con la que trabajamos a lo largo de este proyecto presenta, en un primer momento, una estructura relacional. Extraer los datos de un CSV nos llevó rápidamente a presentarlos en forma de tabla, donde cada accidente toma el lugar de una fila con sus muchos atributos como columnas. Por este camino, PostgreSQL fue nuestra primera opción para manipular y consultar los datos que teníamos a disposición. Pero pronto nos encontramos con los campos de posición geográfica y consultas que debían filtrar por estos atributos que no son el fuerte de las bases relacionales. Por ello mismo también optamos por representar a los datos de forma desestructurada, sin formar relaciones, e hicimos uso del motor MongoDB, que, por defecto, tiene incorporado el tipo de dato GeoPoint. De esta forma, y utilizando las funciones nativas de MongoDb, resolvimos aquellas consultas que involucraban búsquedas y filtrados sobre puntos geográficos.

Es así como notamos que cada motor se adaptaba mejor a consultas particulares. Vimos que, a veces, nuestros cuerpos de datos necesitan, no solo de uno, sino de diversos motores de búsquedas y deben estructurarse en distintos tipos de bases de datos para poder ser analizados con la precisión y performance que se requiere. Búsquedas como hallar los accidentes entre ciertas fechas, son resueltas de forma muy directa a través de una consulta en SQL utilizando PostgreSQL, pero otras, como filtrar por radio de cercanía requieren de otras herramientas que un lenguaje de consultas estructuradas no provee. Es aquí donde MongoDB y ElasticSearch nos brindan sus servicios.
