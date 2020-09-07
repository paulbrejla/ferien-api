<!-- TABLE OF CONTENTS -->
## ferien-api.de

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
## About The Project

![screenshot](product.png "ferien-api.de")

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

3. Run 
```sh
./gradlew bootRun
```
