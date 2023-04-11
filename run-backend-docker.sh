# linux
docker run -d -p 8089:8089 --name backend-hoangtrinh -v "$(pwd):/app" --network charity-network trinhhoang19020301/charity-backend:1.0.0.0
# windows
#docker run -d -p 8089:8089 --name backend-hoangtrinh -v ${PWD}:/app --network charity-network trinhhoang19020301/charity-backend:1.0.0.0