 [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![ferien-api](https://circleci.com/gh/paulbrejla/ferien-api.svg?style=shield)](https://app.circleci.com/pipelines/github/paulbrejla/ferien-api)

This is where the code for <a href="https://ferien-api.de">ferien-api.de</a> lives.

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)



<!-- ABOUT THE PROJECT -->
## About

![screenshot](product.png "ferien-api.de")

<!-- BUILT WITH -->
### Built With
This project is built with Spring Boot and Kotlin, using an embedded h2 database
as its data store.

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Installation

1. Clone the repo
```sh
git clone https://github.com/paulbrejla/ferien-api.git
```

2. Add _.ics_ calendar files to _/main/resources/holidays_
 - Filenames need to conform to the following format:
   ```
   ferien_{state}.ics
   e.g. ferien_Bremen.ics
   ```

 3. Configure environment variables to load _.ics_ files from the classpath or from a git repo

| Property  | filesystem (classpath) | git                                                         |
|-----------|------------------------|-------------------------------------------------------------|
| source    | `filesystem`           | `git`                                                         |
| remoteURL | Not needed             | Git URL e.g. https://github.com/paulbrejla/ferien-api.git   |
| branch    | Not needed             | Branch e.g. `master`                                          |
| filePath  | Not needed             | Path to look up ics files e.g. `src/test/resources/holidays/` |

4. Run 
```sh
./gradlew bootRun
```

### Build with Docker

1. Build Docker Image
```sh
docker build -t holidays-api .
```

2. Tag Docker Image
```sh
docker tag holidays-api:latest remote-repo/holidays-api:latest 
```

3. Push Docker Image to remote repo
```sh
docker push remote-repo/holidays-api:latest 
```

<!-- LICENSE -->
### License
Distributed under the MIT License. See LICENSE for more information.

<!-- CONTRIBUTING -->
### Contributing

When contributing to this repository, please first discuss the change you wish to make via issue, email, or any other method with the owners of this repository before making a change.

#### Pull Request Process

1. Ensure any install or build dependencies are removed before the end of the layer when doing a build.
2. Update the README.md with details of changes, this includes new environment variables, exposed ports, useful file locations and container parameters.

### Contact
Paul Brejla - paul(at)paulbrejla.com

Project Link: https://github.com/paulbrejla/ferien-api
