![Java CI](https://github.com/kwin/cppcheclipse/workflows/Java%20CI/badge.svg)

cppcheclipse is an Eclipse plugin which integrates [cppcheck](https://cppcheck.sourceforge.io) with the [CDT project](https://eclipse.org/cdt/). You can run/configure cppcheck from the Eclipse UI.

To build the project on the command line it requires maven, Run the following commands:
```bash
cd com.googlecode.cppcheclipse.parent
mvn clean verify
```
It will not increment the version number nor deploy/publish/release the artifact. You can find the built p2 repository now in ../com.googlecode.cppcheclipse.repository/target in zip format.

**NOTES:**

* This plugin was originally developed in [cppcheclipse project](https://github.com/kwin/cppcheclipse).
* Maven (3.9+) build will work under Java 17.
* Under Windows the `mvn clean verify` will fail because the unit test paths in unix format. As workaround skip the tests `mvn clean package` should build successfully, but it will not create.


Further information on how to use and install cppcheclipse can be found in the [wiki](https://github.com/kwin/cppcheclipse/wiki).
