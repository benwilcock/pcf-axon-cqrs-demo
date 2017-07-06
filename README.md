# CQRS and Event Sourcing on Pivotal Cloud Foundry

This project demonstrates the the use of CQRS and Event Sourcing with Pivotal Cloud Foundry. It contains two Spring Boot applications built using the excellent Axon Framework (Version 3). There are two applications in this project because the command-and-query-responsibility-separation has been implemented quite literally in the code by splitting the *command-side* from the *query-side*. 

This demo was inspired by a webinar given by [Josh Long](https://twitter.com/starbuxman) of Pivotal and [Allard Buijze](https://twitter.com/allardbz) of Trifork. [You can view the whole thing on Youtube](https://youtu.be/Jp-rW-XOYzA).

**This particular demo ONLY runs on CloudFoundry!**

## Before You Begin.

This demo will *only* run in a Cloud Foundry environment such as Pivotal Web Services (PWS) or PCF-Dev. If you don't have a PWS account, [you can sign up for one for free](https://run.pivotal.io). If you do have a PWS account, check you haven't reached your quota, or tidy up your space if you have. 

Clone this repository and go to it's directory in your terminal.

## Setting Up Your Cloud Foundry Space.

This demo needs certain CloudFoundry marketplace services to be available in the space where you want to `cf push` the applications. The services required are MySQL, RabbitMQ, Spring Cloud Config and Spring Cloud Registry. 

To set up these marketplace services, login to CloudFoundry with your local CloudFoundry CLI client. [Download the the 'cf-cli' and install it if you havn't used it before](https://github.com/cloudfoundry/cli).

````bash
$ cf login -a api.run.pivotal.io
````

When logging in, be sure to choose an 'Org' and 'Space' with ample quota.

Now we're logged in, we can setup the supporting services required for our apps. Use the commands below to instantly self-provision services for *MySQL*, *RabbitMQ*, *Spring Cloud Registry* and *Spring Cloud Config*. Note that the Config server will need to know where to get it's config from. A setup file has been provided in the root of this project that contains [the location](https://github.com/benwilcock/app-config).

````bash
$ cf create-service cleardb spark mysql
$ cf create-service cloudamqp lemur rabbit
$ cf create-service p-service-registry standard registry
$ cf create-service p-config-server standard config -c config-server-setup.json
````

A quick call to `cf services` should list all four of these application services (rabbit, mysql, registry and config). They will now be availablefor use by the applications in the targeted space.

## Build & Deploy the Applications.

Now that's done we're ready to compile the code and push the resulting Java JAR to CloudFoundry. From the project root folder, use Gradle Wrapper to build the code and the cf-cli to push the code to CloudFoundry as follows...

````bash
$ ./gradlew build
$ cf push
````

This will compile and push two seperate application JAR files using a CloudFoundry `manifest.yml` that has also been provided. Once deployed to Cloud Foundry, the **command-side** application deals with the *commands* in the domain and emits *events*, and the **query-side** application listens for events and builds a read-only *view* (sometimes called a *projection*) based on the events it receives.

Once the applications are pushed, you can list the apps to find out their URL's. Make a note of them, you'll need these URL's when integration testing the application in the next section.

````bash
$ cf apps

name                               requested state   instances   memory   disk   urls
pcf-axon-cqrs-demo-command-side    started           1/1         1G       1G     pcf-axon-cqrs-demo-command-side.cfapps.io
pcf-axon-cqrs-demo-query-side      started           1/1         1G       1G     pcf-axon-cqrs-demo-query-side.cfapps.io
````

## Integration Testing The Application.

To make this easy, we can simply use curl command to test that the *command-side* and *query-side* applications are collaborating together as intended. A bash script has been provided to make this straightforward. This script assumes Mac OS X, but with a small mod you can use it on Linux too.

````bash
$ ./addProductToCatalog.sh
````

This script POST's a **New Product Command** (a JSON string) to the *command-side* application's `/add`. The script then queries the *query-side* application's "all products" endpoint (`/products`) to check that the new Product we added is in the list. 

> Note: This is not guarenteed to happen instantly (often termed "eventual consistency"), so you may need to be patient between your POST and GET requests. One second should normally do it.

# How Does It Work?

Following our first `curl` command (to the command side)...

1. The command is received by the command-side and then processed by Axon using a new *AggregateRoot* (the term *AggregateRoot* comes from Domain Driven Design, for which CQRS and Event Sourcing are an excellent match!). 

2. The AggregateRoot performs the command and then emits an *Event*.

3. The Event is stored in the *Event Store* (which uses MySQL) anf then gets published to 'out of process' listeners (using RabbitMQ).

4. The query-side *Listener* receives the event and stores a new Product in it's Product view table (also in MySQL, but in a completely seperate Table, Schema and even DB if desired).

Following our second `curl` command (to the query-side)...

1. The Product view would have been queried for a list of products to display.

2. The list of Procuct are sent back to the caller in JSON format.

> Note: Spring Data REST Repositories are being used for the query-side implmentation, so you'll find very little boilerplate code in that part of the project.


# Running locally using PCF-Dev

If you have a development machine with the necessary hardware requirements, you can also run this demo locally using PCF-Dev on your PC. If you don't yet have PCF-Dev, [download it and set it up using these instructions](https://pivotal.io/pcf-dev). 

If you decide to use PCF-Dev, here are some differences to take into account:-

1. You'll need to start PCF-Dev with Spring Cloud Services available.
2. You'll need to login using the PCF-dev endpoint and skipping SSL validation `cf login -a https://api.local.pcfdev.io --skip-ssl-validation`.
3. You'll need to modify the script that creates the services so that you use the correct service names and plans for PCF-Dev (as these have different names from those in the PWS marketplace).
4. The testing endpoints will be different.

Other than these points, the instructions are broadly the same.

# Bonus Material

Want to know [what Greg Young thinks of Axon?](https://www.infoq.com/presentations/event-sourcing-jvm)

Want an automated build? - This project includes a simple continuous delivery pipeline that uses [Concourse](http://concourse.ci/). The Concourse pipeline can be found in the `ci` folder. To use it, you'll need a [Concourse server](http://concourse.ci/), and the associated `fly` cli tool. When pushing the pipeline you'll also need a second file with your private config as follows...

````yml
cf-cmd-app-url: <your command-side app URL>
cf-qry-app-url: <your query-side app URL>
cf-endpoint: <your cf-api endpoint>
cf-user: <your cf username>
cf-password: <your cf password>
cf-org: <your cf org name>
cf-space: <your cf space name>
skip-ssl: <whther you should login with ssl (true|false)>
````

# Credits

[Ben Wilcock](https://benwilcock.wordpress.com) - code.

[Aurora Mirea](https://github.com/auramirea) - code.

[Josh Long](https://twitter.com/starbuxman) - webinar.

[Allard Buijze](https://twitter.com/allardbz) - webinar.
