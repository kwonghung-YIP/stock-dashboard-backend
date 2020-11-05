# Stock Quote Dashboard - Spring WebFlux Backend

Stock Quote Dashboard is a end-to-end demo for how to boost the network throughput between browser and REST API backend using HTTP/2 protocol, this repo is the Spring WebFlux implementation, and for the React frontend part, you find in another [repo](https://github.com/kwonghung-YIP/stock-dashboard-frontend).

![Architecture](/architecture.png)

This repo contains 4 modules
* quote-weblux-api - the Embedded Netty backend to query the market data cached in MongoDB Altas.
* datafeed-scheduler - the schedule job to get market data from IEX Cloud API and write it into MongoDB Altas as cache. 
* core-domain-library - the pojo and other common library to share among both above modules
* iexcloud-api-wrapper - the REST API client wrapper for IEX Cloud API

# To run the on your own
