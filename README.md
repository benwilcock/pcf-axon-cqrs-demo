# CQRS and Event Sourcing on Pivotal Cloud Foundry

This project demonstrates the the use of CQRS and Event Sourcing with Pivotal Cloud Foundry. It contains two Spring Boot applications built using the excellent Axon Framework (Version 3). This demo was inspired by a webinar given by Josh Long of Pivotal and Allard Buijze of Trifork. [You can view the whole thing on Youtube](https://youtu.be/Jp-rW-XOYzA).

**This particular demo ONLY runs on CloudFoundry!**

## Before You Begin

This demo will *only* run in a Cloud Foundry environment such as Pivotal Web Services (PWS) or PCFDev. If you don't have a PWS account, [you can sign up for a free](https://run.pivotal.io). If you do have a PWS account, check you haven't reached your quota, or tidy up if you have. 

This demo also needs certain CloudFoundry marketplace services to be available in the space where you want to `cf push` the applications. The services required are MySQL, RabbitMQ, Spring Cloud Config and Spring Cloud Registry. 

To set up these marketplace services, login to CloudFoundry with your local CloudFoundry CLI client. [Download the the 'cf-cli' and install it if you havn't used it before](https://github.com/cloudfoundry/cli).

````bash
$ cf login -a api.run.pivotal.io
````

When logging in, be sure to choose an 'Org' and 'Space' with ample quota.

Now we're logged in, we can setup the supporting services required for our apps. Use the commands below to create services for MySQL, RabbitMQ, Spring Cloud Registry and Spring Cloud Config. Note that the Config server needs to know where it's config is being stored. A setup file has been provided in the root of this project that contains [the location](https://github.com/benwilcock/app-config).

````bash
$ cf create-service cleardb spark mysql
$ cf create-service cloudamqp lemur rabbit
$ cf create-service p-service-registry standard registry
$ cf create-service p-config-server standard config -c config-server-setup.json
````

A quick call to `cf services` should list all four of these application services (rabbit, mysql, registry and config). They will now be available to the applications in the targeted space.

Now we're ready to compile the code and push the resulting Java JAR to CloudFoundry. From the project root folder, use Gradle Wrapper to build the code and the cf-cli to push it to CloudFoundry as follows...

````bash
$ ./gradlew build
$ cf push
````

This will compile and push two seperate application JAR files. One application deals with the *commands* in the domain and emits *events*, and the other listens to the events and builds a read-only *view* (sometimes called a *projection*).

Once the applications are pushed, you can list the apps and find our their URL's using the cf-cli.

````bash
$ cf apps

name                               requested state   instances   memory   disk   urls
pcf-axon-cqrs-demo-command-side    started           1/1         1G       1G     pcf-axon-cqrs-demo-command-side.cfapps.io
pcf-axon-cqrs-demo-query-side      started           1/1         1G       1G     pcf-axon-cqrs-demo-query-side.cfapps.io
````

You'll need these URL's to test the application is working. 


## Testing the application.

To make this easy, we can simply use curl to test the *command-side* and *query-side* applications are collaborating together as intended. A bash script has been provided to make this easy.

````bash
$ ./addProductToCatalog.sh
````

This script creates a **New Product Command** (a JSON string) and then uses the `/add` endpoint to POST the command to the *command-side* of the application. The script then queries the "all products" endpoint (`/products`) on the *query-side* of the application to check that the new Product we added is listed. This is not guarenteed to happen instantly (this is called "eventual consistency"), so you may need to be patient between requests.


# How Does It Work?

Following our first `curl` command (to the command side)...

1. The command will have been received by the command-side and processed by a new *AggregateRoot*. 
2. The Aggregate would have emitted an *Event*
3. The Event would have been data stored in the *Event Store* (which uses MySQL)
4. The events emitted would have been published to 'out of process' listeners (using RabbitMQ).
5. The query-side *Listener* would have reacted to the event, storing a new Product in it's product view.

Following our second `curl` command (to the query-side)...

1. The Product view would have been queried for a list of products to display.


# Running locally using PCF-Dev

If you have a development machine with the necessary hardware requirements, you can also run this demo locally using PCF-Dev on your PC. If you don't yet have PCF-Dev, [download it and set it up using these instructions](https://pivotal.io/pcf-dev). 

If you decide to use PCF-Dev, here are some differences to take into account:-

1. You'll need to start PCF-Dev with Spring Cloud Services available.
2. You'll need to login using the PCF-dev endpoint and skipping SSL validation `cf login -a https://api.local.pcfdev.io --skip-ssl-validation`.
3. You'll need to modify the script that creates the services so that you use the correct service names and plans for PCF-Dev (as these have different names from those in the PWS marketplace).
4. The testing endpoints will be different.

Other than these points, the instructions are broadly the same.

# Bonus Material

Concourse

# Credits

Aurora