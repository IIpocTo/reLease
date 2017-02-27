# <img src="https://github.com/eakarpov/justrent/blob/master/src/logo/main-var-1.png" width="10%" height="10%" alt="" /> 
## [![Build Status](https://travis-ci.org/IIpocTo/reLease.svg?branch=master)](https://travis-ci.org/IIpocTo/reLease) [![bitHound Overall Score](https://www.bithound.io/github/IIpocTo/reLease/badges/score.svg)](https://www.bithound.io/github/IIpocTo/reLease) [![bitHound Dependencies](https://www.bithound.io/github/IIpocTo/reLease/badges/dependencies.svg)](https://www.bithound.io/github/IIpocTo/reLease/master/dependencies/npm)

Requirements:

1. Java 8 - http://www.oracle.com/technetwork/java/javase/downloads/index.html
2. Maven - https://maven.apache.org/download.cgi
3. Node JS - https://nodejs.org/en/download/current/
4. Yarn - https://yarnpkg.com/en/docs/install

Backend (http://localhost:8080/swagger-ui.html - REST API Docs)
```
mvn clean package -DskipTests=true
mvn spring-boot:run
```

Frontend (http://localhost:4200 - Web site main page)
```
cd frontend
yarn install
yarn start
```
