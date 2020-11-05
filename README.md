# Stock Quote Dashboard - Spring WebFlux Backend

Stock Quote Dashboard is a end-to-end demo for how to boost the network throughput between browser and REST API backend using HTTP/2 protocol, this repo is the Spring WebFlux implementation, for the React frontend part, you can find it [here](https://github.com/kwonghung-YIP/stock-dashboard-frontend).

![Architecture](/architecture.png)

This repo contains 4 maven sub-modules
* quote-webflux-api - the Embedded Netty backend to query the market data cached in MongoDB Altas.
* datafeed-scheduler - the schedule job to get market data from IEX Cloud API and write it into MongoDB Altas as cache. 
* core-domain-library - the pojo and other common library to share among both above modules
* iexcloud-api-wrapper - the REST API client wrapper for IEX Cloud API

## Run quote-webflux-api backend

Before start the spring boot project, first you have to define following properties to specify your altas login.

```yaml
mongodb-altas:
  login: <your-mongodb-altas-login>
  passwd: <your-mongodb-altas-password>
  cluster-domain: <your-mongodb-altas-domain>
```
## Run datafeed-scheduler to get market data

Before start the spring boot project, first you have to define following properties to specify your IEX API token and altas login.

```yaml
iex-cloud:
  base-url: https://cloud.iexapis.com/stable/
  api-token: <your iex cloud token>
  
mongodb-altas:
  login: <your-mongodb-altas-login>
  passwd: <your-mongodb-altas-password>
  cluster-domain: <your-mongodb-altas-domain>
```
