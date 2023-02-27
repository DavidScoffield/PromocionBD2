# TRABAJO DE PROMOCIÓN BD2

##

## **Endpoints**

A continuación describiremos los endpoints por medio de los cuales se pueden realizar las consultas HTTP requeridas a las distintas bases de datos:

( ***Todo endpoint debe comenzar con "GET http://localhost:8080/accidents"*** )


1.  > *Retornar una lista de los accidentes ocurridos entre 2 echas dadas.*
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
    >Ejemplo:
    >
    >- GET http://localhost:8080/accidents/between-dates?start=08-02-2016&end=09-02-2016&page=2&perPage=10 HTTP/1.1

<br></br>

2.  > *Retornar lista de accidentes ocurridos a una distancia (radius) en km de un punto geográfico*
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
    >.../nearLocation?longitude=[P]&latitude=[Q]&radius=[N]
    >```
    >Ejemplos:
    >
    >- GET <http://localhost:8080/accidents/near?longitude=-84.058723&latitude=39.865147&radius=10> HTTP/1.1
    >
    >- GET <http://localhost:8080/accidents/nearLocation?longitude=-84.058723&latitude=39.865147&radius=10> HTTP/1.1

<br></br>

3.  > *Retornar la distancia promedio (en metros) desde el inicio al fin del accidente*
    > 
    >
    >```
    >.../average-distance
    >```
    >Ejemplo:
    >
    >- GET http://localhost:8080/accidents/average-distance HTTP/1.1

<br></br>

4.  > *Retorna las 5 calles con más accidentes*
    > 
    >```
    >.../dangerous-streets
    >```
    >Ejemplo:
    >
    >- GET http://localhost:8080/accidents/dangerous-streets HTTP/1.1

<br></br> 


5.  > *Retornar la distancia promedio (en km) de cada accidente a los 10 más cercanos*
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
    >Ejemplos:
    >
    >- GET <http://localhost:8080/accidents/near/average-distance?page=1&perPage=10> HTTP/1.1
    >
    >- GET <http://localhost:8080/accidents/near/average-distance2?page=1&perPage=10> HTTP/1.1

<br></br>

6.  > *Retornar las condiciones más comunes en los accidentes*
    > 
    >```
    >.../getCommonConditions
    >```
    >Ejemplo:
    >
    >- GET http://localhost:8080/accidents/getCommonConditions HTTP/1.1

<br></br>

7.  > *Retornar los puntos más peligrosos a partir de un determinado radio.*
    > 
    > - **Parametros:**
    >   - *radius*  - el radio de la circunferencia
    >   - *amount*  - la cantidad de puntos a obtener
    > <br></br>
    >```
    >.../getMostDangerousPoints?radius=[N]&amount=[M]
    >```
    >Ejemplo:
    >
    >- GET http://localhost:8080/accidents/getMostDangerousPoints?radius=2&amount=5 HTTP/1.1

<br></br>

## 