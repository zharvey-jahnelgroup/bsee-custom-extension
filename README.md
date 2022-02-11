# bsee-custom-extension
Troubleshooting repo for getting BSEE custom extensions to load and run on EE scans.

## Objective
The objective of this extension is to add an `Authorization: Bearer` JWT to each and every request that BSEE makes during a scan.

## Building Locally
Run:
```
./gradlew clean fatJar -Pversion=<VERSION>
```

Where `<VERSION>` is the version you are building. We recommend sticking with a semantic versioning paradigm.