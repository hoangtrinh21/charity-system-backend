docker run -d --rm --name mysql-charity-hoangtrinh -v mysql-charity-data:/var/lib/mysql -v mysql-charity-config-deamond:/etc/mysql/conf.d -p 3310:3306 -p 33100:33060 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_USER=dev -e MYSQL_PASSWORD=123456 -e MYSQL_DATABASE=charity --network charity-network mysql:latest
# linux
docker run -d --rm -p 8089:8089 --name backend-hoangtrinh -v "$(pwd):/app" --network charity-network trinhhoang19020301/spring-boot-docker:1.0.0.1
# windows
#docker run --rm -p 8089:8089 --name backend-hoangtrinh -v "%cd%:/app" --network charity-network trinhhoang19020301/spring-boot-docker:1.0.0.1