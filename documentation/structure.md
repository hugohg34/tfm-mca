## Estructura del proyecto monorepo

```
mca-tfm/
├── .git/                                 # Git version control directory
├── applications/                         # All applications (Spring Boot and Node.js)
│   ├── spring-boot-apps/                 # Spring Boot applications
│   │   ├── application1/                 # Spring Boot Application 1
│   │   │   ├── src/
│   │   │   ├── test/
│   │   │   └── pom.xml
│   │   ├── application2/                 # Spring Boot Application 2
│   │   │   ├── src/
│   │   │   ├── test/
│   │   │   └── pom.xml
│   │   └── ...
│   ├── nodejs-apps/                      # Node.js applications
│   │   ├── nodejs-app1/                  # Node.js Application 1
│   │   │   ├── src/
│   │   │   ├── test/
│   │   │   └── package.json
│   │   ├── nodejs-app2/                  # Node.js Application 2
│   │   │   ├── src/
│   │   │   ├── test/
│   │   │   └── package.json
│   │   └── ...
│   └── ...
├── shared-libraries/                     # Shared code and libraries
│   ├── grpc-protos/                      # .proto files for gRPC
│   │   ├── service1.proto
│   │   └── service2.proto
│   ├── common-utils/                     # Common utilities and helper classes
│   │   ├── src/
│   │   └── pom.xml
│   └── ...
├── infrastructure/                       # Scripts and configurations for infrastructure
│   ├── docker/
│   ├── kubernetes/
│   └── ...
├── scripts/                              # Scripts for automation
├── documentation/                        # Project documentation
│   ├── README.md
│   └── ...
├── pom.xml                               # Root pom.xml for managing Spring Boot sub-modules
└── .gitignore                            # .gitignore file

```

