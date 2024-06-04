FROM mysql:8.3.0

COPY init.sql /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=cardapioautomatizado
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=gabriel12

EXPOSE 3306
