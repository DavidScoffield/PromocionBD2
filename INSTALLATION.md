# Guia de instalación y ejecucion de la aplicación

`Los comandos indicados a continuación deben correrse desde el directorio raíz del proyecto`
## 1. Instalacion del ambiente

1. Copiar el archivo CSV (Ejemplo: US_Accidents_Dec19.csv) al directorio /initialization/csv

2. Ingresar en el archivo .env, como valor de la variable NAME_CSV_FILE, el nombre de archivo del csv sin la extensión.

   > A continuacion se explican los valores de configuracion modificables en el archivo .env:

   ```sh
    # Postgres configuration
    POSTGRES_DB : nombre de la base de datos de postgres
    POSTGRES_USER : nombre de usuario de postgres
    POSTGRES_PASSWORD : contraseña de postgres

    # Mongo configuration
    MONGO_DB : nombre de la base de datos de mongo
    MONGO_USER : nombre de usuario de mongo
    MONGO_PASSWORD : contraseña de mongo

    # HOST PORTS
    SERVER_HOST_PORT : puerto que se expondra en el host que corre el contenedor de la aplicacion
    MONGO_HOST_PORT : puerto que se expondra en el host que corre el contener de mongo
    POSTGRES_HOST_PORT : puerto que se expondra en el host que corre el contenedor de postgres

    # CSV
    NAME_CSV_FILE : nombre del archivo csv sin la extensión
   ```

3. Ejecutar

   ```
   docker-compose up -d
   ```
4. Una vez creada la imagen y los contenedores, la importación del archivo csv a las bases de datos se realizará en segundo plano.
Podrá seguir su estado con el siguiente comandos:

    ```
    docker logs {nombre_contenedor}
    ```

## 2. Dar de baja ambiente

1. Ejecutar

   ```
   docker-compose down
   ```
## 3. Cargar base de datos MANUALMENTE

### 3.1 Carga de la base de datos de _Mongo_

(con el contenedor de mongo iniciado)

#### Ejecutar:

```sh
docker exec  -it {nombre_contenedor_mongo} bash /docker-entrypoint-initdb.d/mongoDataInit.sh
```

### 3.2 Cargar base de datos de _Postgres_

(con el contenedor de postgres iniciado)

#### Ejecutar:

```sh
docker exec  -it {nombre_contenedor_postgres} bash /docker-entrypoint-initdb.d/postgresDataInit.sh
```

## 4. Posibles soluciones a problemas

### 4.1 _Error al inicializar base de datos_

#### 4.1.1 [ Solucion 1 ]

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

#### 4.1.2 [ Solucion 2 ]

1. Mantener los contenedores levantados, o levantarlos en caso de que esten detenidos.

2. Cargar las bases de datos manualmente como se describe en el [punto 3](#3-cargar-base-de-datos-manualmente).
