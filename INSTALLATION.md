# Guia de instalación y ejecucion de la aplicación

## 1. Instalacion del ambiente

1. Copiar US_Accident_Data.csv al directorio /initialization/csv

2. Ejecutar docker-compose up -d

## 2. Cargar base de datos MANUALMENTE

### 2.1 Carga de la base de datos de _Mongo_

(con el contenedor de mongo iniciado)

#### Ejecutar:

```sh
docker exec  -it {nombre_contenedor_mongo} bash /docker-entrypoint-initdb.d/mongoDataInit.sh
```

### 2.2 Cargar base de datos de _Postgres_

(con el contenedor de postgres iniciado)

#### Ejecutar:

```sh
docker exec  -it {nombre_contenedor_postgres} bash /docker-entrypoint-initdb.d/postgresDataInit.sh
```

## 3. Posibles soluciones a problemas

### 3.1 _Error al inicializar base de datos_

#### 3.1.1 [ Solucion 1 ]

1.  Dar de baja los contenedores:

    ```sh
    docker-compose down
    ```

2.  Eliminar los volumenes de las bases de datos:

    **Mongo:**

    ```sh
    docker volume rm {nombre_volumen_mongo}
    ```

    ( {nombre_volumen_mongo} = ${nombre_carpeta_del_proyecto}\_bd-mongo )Ejemplo: promocionbd2_bd-mongo

    **Postgres:**

    ```sh
    docker volume rm {nombre_volumen_postgres}
    ```

    ( {nombre_volumen_postgres} = ${nombre_carpeta_del_proyecto}\_bd-postgres ) Ejemplo: promocionbd2_bd-postgres

3.  Levantar los contenedores:

    ```sh
    docker-compose up -d
    ```

#### 3.1.2 [ Solucion 2 ]

1. Mantener los contenedores levantados, o levantarlos en caso de que esten detenidos.

2. Cargar las bases de datos manualmente como se describe en el [punto 2](#2-cargar-base-de-datos-manualmente).
