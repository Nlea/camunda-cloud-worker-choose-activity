# camunda-cloud-worker-choose-activity
A worker written with Java and Springboot to fetch and log service tasks from Camunda Cloud and complete them. 

 **prerequirements**:exclamation: \
In order to run the worker you need to make sure that a process is deployed to Camunda Cloud, an instance of it has been started and that a service task with the right type is available. You can find the matching process to the worker [here](https://github.com/Nlea/camunda-cloud-corona-update-process) as well as all the information how to get an account and set up a cluster, which will be needed for the worker as well.

**set up the worker** \
Make sure to load all the maven dependencies that are included in the pom file. In order to connect to Camunda Cloud create a application.properties file in the main/ ressource folder. In that file provide your Camunda Cloud Cluster credentials. You can find an example [here](https://docs.camunda.io/docs/guides/setting-up-development-project#configure-connection) 

If everything is set up you can start the CloudWorkerChooseActivityApplication class. :tada:


If you would build your own worker form sretch you can follow this [tutorial](https://docs.camunda.io/docs/guides/setting-up-development-project#prerequisites)

-------------------------------------
## Run the worker on Kubernetes in the cloud

If you want to run the worker continuously (in a more realistic environment) it is a good idea not to run it on your machine. To deploy it to Kubernetes on a cloud provider the following steps are needed:

* Create a Docker image. If you want to publish it later on Docker hub make sure to exclude your credentials from the application.properties as they can be extracted from the docker image later
* Publish your Docker image at [Docker hub](https://hub.docker.com/) or you can use the image I created for the worker there: https://hub.docker.com/repository/docker/nlea/worker-send-telegram-message
* In order to communicate with Kubernetes make sure you have [kubectl](https://kubernetes.io/docs/tasks/tools/) installed on your maschine
* For the deployment to Kubernetes the project uses the `deploy.yml` file which describe a Kubernetes Deployment resource. As the docker image does not contain the credentials, you need to provide them by creating a [kubernetes secret](https://kubernetes.io/docs/concepts/configuration/secret/)  
```
kubectl create secret generic mysecret --from-literal=zeebeaddress
=xxxxx--from-literal=zeebeclientid=xxxxxx--from-literal=zeebeclientsecret=xxxxx--from-literal=zeebeauthorization=xxxxx
```

* Then link it inside the `deploy.yml` file using Environment Variables. In the `deploy.yml` you can find in this project the name of the secret is “mysecret”. In the example below you can see how the zeebeaddress is linked in the deploy.yml:  

```
        env: 
        - name: ZEEBE_ADDRESS
          valueFrom:
            secretKeyRef:
              name: mysecret
              key: zeebeaddress
``` 

Now you can deploy your Docker image to the Kubernetes cluster 🎉 
