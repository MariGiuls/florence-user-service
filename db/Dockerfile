FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=florence
ENV MYSQL_DATABASE=florence
ENV MYSQL_USER=florence
ENV MYSQL_PASSWORD=florence

COPY init.sql /docker-entrypoint-initdb.d/
