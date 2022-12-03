# APPLICATION_NAME

> Azure [Java] Function bootstrap project

<p align="center">
  <a href="https://azure.microsoft.com/en-us/services/functions/"><img width="100%" src="https://minio.reuse.org.com/images/functions.jpg"></a>
</p>

## PreRequisites

- [Java Developer Kit](https://aka.ms/azure-jdks) (JDK), version 1.8.
- [Apache Maven](https://maven.apache.org), version 3.0 or above.
>**Important**: The `JAVA_HOME` environment variable must be set to the install location of the JDK to complete this.
- [kubernetes](https://paymentintegrity.org.com/onboarding/local-kubernetes/)
- [azure functions core tools](#install-core-tools)
- [helm3](https://helm.sh/docs/intro/install/)

## Overview

This project is a java based Azure Function Application that comes pre-configured with 2 `httpTrigger` function.
The project structure is defined below

```cs
root
 | - src
 | | - main
 | | | - java
 | | | | - function
 | | | | | - HttpTrigger1.java
 | | | | | - HttpTrigegr2.java
 | - target
 | | - azure-functions
 | | | - function
 | | | | - azure-function-java-boot.jar
 | | | | - host.json
 | | | | - http-trigger1
 | | | | | - function.json
 | | | | - http-trigger2
 | | | | | - function.json
 | | | | - bin
 | | | | - lib
 | - pom.xml
 ```

## Commands

### Install dependencies

```cs
mvn install
```

### Build

```cs
mvn package
```

### Test

```cs
mvn test
```

### Run

```cs
func start
```

```

### Deploy to local kubernetes with HELM

 [HELM](https://helm.sh/docs/intro/install/) is required for this section*

```sh
$ ./local.sh
```

*_You should now be able to hit your new application at [http://localhost:7071](http://localhost:7071)_*

## Jenkins Deployments

The azure-function-java-boot supports 2 deployment models:  

1. [Kubernetes](./jenkins.md#-deployment---kubernetes)
2. [Azure](./jenkins.md#-deployment---azure) (coming soon)

*_NOTE_ - You will need to update the repository in your prod.yaml file for NON-LOCAL HELM Deployments*

## Additional Links

[Azure Function Java](https://code.visualstudio.com/docs/java/java-azurefunctions)  

