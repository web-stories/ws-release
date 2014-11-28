# Release

Web Stories release process.

## Requeriments

The following dependencies are required to be installed and accessible in the command line when executing this release process:

* [Maven](http://maven.apache.org/download.cgi)

## What does it do?

It runs a series of custom tasks relevant to the production release process:

* Ensures the current branch is the `master` branch, otherwise fail.
* Pull all changes from the current `master` branch to ensure it is up to date.
* Execute maven full build process.
* Check if the new artifact is different from the deployed one.
* Stop the production server.
* Remove the old deployed artifact.
* Copy the new deployed artifact to the production server.
* Start the production server

## Getting started

Make sure you have java installed and run the following command inside the directory where lies the project's main `pom.xml` file:

```shell
$ java -jar release.jar
```

## Options

#### --jboss="/path"

The root directory path of the JBoss Server where the artifact is going to be deployed into.
