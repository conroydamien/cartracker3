# cartracker3

This application was generated using JHipster 3.10.0, you can find documentation and help at [https://jhipster.github.io/documentation-archive/v3.10.0](https://jhipster.github.io/documentation-archive/v3.10.0).

This is a bit of a horrible hack that was pulled together pretty quickly!

These instructions are heavily biased towards the Ubuntu/Linux user. If you're trying this on anything else you're on your own! 

## Quick Route

Get node and npm working on an Ubuntu instance. [Look here.](https://steemit.com/ethereum/@brennanhm/ethereum-smart-contract-testing-installing-truffle-and-testrpc).

Get Docker Compose working - [Docker Compose][DockerComposeUbuntu].

From the directory containing package.json run the command 'npm install'.

Run the command 'npm install -g gulp-cli' (this might need a sudo).

Run the command 'gradle bootRepackage'

Run the command 'gradle buildDocker'

Run the command 'docker-compose -f src/main/docker/app.yml up'

The app should now be available at port 8080 on the host.

If the quick route didn't work continue reading below!

## Dependencies

The cartracker3 project is based on JHipster and has dependencies on Postgres and TestRPC. One way to provide a Postgres instance prepared for the project is to use [Docker Compose][DockerComposeUbuntu]. This will ensure that the necessary tables and user roles are available. The Postgres instance must be running before the rest of the application is started.

If you wish to use it, please ensure that you have Docker Compose (at least version 1.9 - earlier versions cause problems) installed and then refer to the [section](#using-docker-to-simplify-development) on using Docker below.

When the database is up you should be able to install [PGAdminIII](https://www.pgadmin.org/) and browse the database. If you cannot see the 10 tables created in a database called cartracker3 then go no further until you can. 

Because the web3 invocations are carried out from the Angular controllers (i.e. the browser, a decision made on the day) the domain name 'testrpc' must resolve to a testrpc instance with port 8545 open. You can just run testrpc locally and add an entry called testrpc to /etc/hosts with the address 127.0.0.1 to get started.


## Development

Before you can build this project, you must install and configure the following dependencies on your machine:
1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Gulp][] as our build system. Install the Gulp command-line tool globally with:

    npm install -g gulp-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./gradlew
    gulp

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

To optimize the cartracker3 application for production, run:

    ./gradlew -Pprod clean bootRepackage

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar build/libs/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./gradlew test

### Client tests

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript/` and can be run with:

    gulp test



For more information, refer to the [Running tests page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the `src/main/docker` folder to launch required third party services.
For example, to start a postgresql database in a docker container, run:

    docker-compose -f src/main/docker/postgresql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/postgresql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./gradlew bootRepackage -Pprod buildDocker

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`yo jhipster:docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To set up a CI environment, consult the [Setting up Continuous Integration][] page.

[JHipster Homepage and latest documentation]: https://jhipster.github.io
[JHipster 3.10.0 archive]: https://jhipster.github.io/documentation-archive/v3.10.0

[DockerComposeUbuntu]:https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-compose-on-ubuntu-14-04
[Using JHipster in development]: https://jhipster.github.io/documentation-archive/v3.10.0/development/
[Using Docker and Docker-Compose]: https://jhipster.github.io/documentation-archive/v3.10.0/docker-compose
[Using JHipster in production]: https://jhipster.github.io/documentation-archive/v3.10.0/production/
[Running tests page]: https://jhipster.github.io/documentation-archive/v3.10.0/running-tests/
[Setting up Continuous Integration]: https://jhipster.github.io/documentation-archive/v3.10.0/setting-up-ci/


[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Gulp]: http://gulpjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
