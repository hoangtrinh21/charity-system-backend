# linux
docker run -d -p 8089:8089 --name backend-hoangtrinh -v "$(pwd):/app" --network charity-network trinhhoang19020301/charity-backend:1.0.0.0
# windows
#docker run --rm -p 8089:8089 --name backend-hoangtrinh -v "%cd%:/app" --network charity-network trinhhoang19020301/spring-boot-docker:1.0.0.1