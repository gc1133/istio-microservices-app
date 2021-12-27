
* There are 2 microservices
  * Product service
    * Port: 9090
    * API: /products/product/{product id}
    * Discount service: accessible at port 9091
  * Discount service
    * Port: 9091
    * API: /discount/{product_id}
  * Product service calls discount service to get the discount applicable for the product

## Commands 
### Running the microservices ***without*** kubernetes cluster
* Goto root project location having **docker-compose.yaml** file
* Start containers
```
docker-compose up -d
```
* Test product service via postman, it calls discount service having 10% code
```
Postman: http://localhost:9090/products/product/1 (GET call)
```
* Push 3 images to docker hub
  * Product service image: 
  * 10% discount service image (java code having 10% discount)
  * 20% discount service image (java code having 20% discount)
```
docker push gc1133/product-service-without-mysql:1.0 
docker push gc1133/discount-service-without-mysql:1.0 (image having 10% discount code)
docker push gc1133/discount-service-without-mysql:2.0 (image having 20% discount code)
docker-compose down --rmi 'all'
```

### Running the microservices in kubernetes cluster
* We will run 1 instance of product service and 2 instances of discount service (1 with 10% discount and another with 20% discount)
* k8s deployment and service yaml files can be found in folder k8s
* Create minikube cluster from given yaml file
```
 kubectl apply -f .\1_k8s_without_istio_routes.yml
```
* To access product service from postman, get the service url from below command:
```
 minikube service product-service --url
 while true; do curl -s http://127.0.0.1:62352/products/product/1 | grep discount; sleep 2; done
```
* Actual round robin behavior was not seen here due to load balancer configurations missing in minikube local cluster
* Requests were switching b/w version v1 and v2 apps but after a lot of time ~ 2-3 mins

### Running the microservices in kubernetes cluster with istio mesh

##### Istio installation
* Start istio with below commands
```
istioctl version
Output: no running Istio pods in "istio-system" 1.7.3
```

* Run the following command to configure the Istio profile on Kubernetes:
```
istioctl install --set profile=demo
```
* Verify that Istio was successfully deployed by running the following command:
```
kubectl get deployments -n istio-system
```
* All the values in the AVAILABLE column will have a value of 1 after the deployment is complete.
* Ensure that the Istio deployments are all available before you continue. The deployments might take a few minutes to become available. If the deployments aren’t available after a few minutes, then increase the amount of memory available to your Kubernetes cluster. On Docker Desktop, you can increase the memory from your Docker preferences. On Minikube, you can increase the memory by using the --memory flag
* Finally, create the istio-injection label and set its value to enabled:
```
kubectl label namespace default istio-injection=enabled
```
* Adding this label enables automatic Istio sidecar injection. Automatic injection means that sidecars are automatically injected into your pods when you deploy your application.

##### Deploy apps of both the versions
```
kubectl apply -f .\1_k8s_without_istio_routes.yml
minikube service product-service --url
while true; do curl -s http://127.0.0.1:60820/products/product/1 | grep discount; sleep 1; done
```
### Canary deployment with istio service mesh
```
kubectl get all -n istio-system
kubectl apply -f .\2_k8s_with_istio_routes.yml
while true; do curl -s http://127.0.0.1/products/product/1 | grep discount; sleep 1; done
```
Here you will see the accurate round robin requests b/w both the version of apps

## Tearing down your environment
You might want to teardown all the deployed resources as a cleanup step.

* Delete your resources from the cluster:
```
kubectl delete -f .\1_k8s_without_istio_routes.yml
```
* Delete the istio-injection label from the default namespace. The hyphen immediately after the label name indicates that the label should be deleted.
```
kubectl label namespace default istio-injection-
```
* Delete all Istio resources from the cluster:
```
istioctl x uninstall --purge
```
kubectl delete -f k8s/1_k8s_without_istio_routes.yml
istioctl dashboard kiali
kubectl apply -f k8s/2_k8s_with_istio_routes.yml