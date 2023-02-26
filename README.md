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
    >GET http://localhost:8080/accidents/between-dates?start=08-02-2016&end=09-02-2016&page=2&perPage=10 HTTP/1.1

<br></br>


2.  > *Retorna lista de accidentes ocurridos a una distancia (radius) en km de un punto geográfico (Version Mongo)*
    > 
    > - **Parametros:**
    >   - *longitude*  - la longitud de la ubicacion del accidente. (Entre -180 y 180)
    >   - *latitude*  - la latitud de la ubicacion del accidente. (Entre 0 y 90)
    >   - *radius*  - el radio de la circunferencia que rodea al accidente. Medido en km.
    > <br></br>
    >```
    >.../near?longitude=[P]&latitude=[Q]&radius=[N]
    >```
    >Ejemplo:
    >
    >GET <http://localhost:8080/accidents/near?longitude=-84.058723&latitude=39.865147&radius=10> HTTP/1.1

## 