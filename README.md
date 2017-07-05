# pcf-axon-cqrs-demo

Demonstrate the use of CQRS and Event Sourcing with PCF using Axon Framework Version 3.

**Runs ONLY on CloudFoundry!**

## Before you start, setup CloudFoundry

This demo will *only* run in a Cloud Foundry environment such as Pivotal Web Services (PWS) or PCFDev. You can sign up for a free development trial of PWS here: https://run.pivotal.io or you can run PCF-Dev locally on your PC by downloading and installing it from here: https://pivotal.io/pcf-dev. 

This demo also needs certain Cloud Foundry marketplace services to be available in the space where you're `cf push`-ing the applications, namely MySQL, RabbitMQ, Spring Cloud Config and Spring Cloud Registry. 

To set up these marketplace services in PWS, login with your cf-cli (`cf login -a api.run.pivotal.io`) then use the script provided...

````bash
$ ./pws-create-pcf-services.sh
````

To to configure your PCFDev environment with the same services, first start PCFDev with Spring Cloud Services 

````bash
$ cf dev start -s all
$ cf dev target
````

Then use this script to create the marketplace services...

````bash
$ ./pcfdev-create-pcf-services.sh
````

Assuming these scripts run OK, a quick call to `cf services` should show you that you have all four application services (rabbit, mysql, registry and config) available to the applications in the targeted application space.

## Launch the Apps in CloudFoundry

Build the code...

````bash
$ ./gradlew build
````

Push the applications to run on Cloud Foundry. A manifest for the command-side and query-side applications has been added to the project folder to make this super simple, just...

````bash
$ cf push
````

The applications will get pushed to the Cloud Foundry, bound to the services you created and will start. To make sure there is no 'route collision' in Cloud Foundry, the manifest gives your apps random URL's. To find out what these URL's are at any time, ask for a listing of your apps...

````bash
$ cf apps
````

To see the app's default homepage, open this URL in your browser.

# Testing the CQRS App demo

The command for adding a product to the catalogue is `add` and it requires a JSON payload containing a GUID and a name. You'll need a plain GUID for your JSON payload. Mac OSX users, use `uuidgen`. Ubuntu users, use `uuid`.

````bash
$ export UUID=`uuid`
$ curl -s -H "Content-Type:application/json" -d "{\"id\":\"${UUID}\", \"name\":\"test-${UUID}\"}" <YOUR-COMMAND-SIDE-URL>/add
````

If all is working, you'll get a return message containing your GUID. The command will have been processed, and your Product Added events will have been published (via RabbitMQ) and stored (in MySQL).