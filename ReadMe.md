# Read Me First
The following topics are the guide to using this project:

### Build
* Build using 'mvn clean install -DskipTests'
* Run test cases using 'mvn test'

### UI Interface
* Under WebSecurityConfig.java configure the user for login and assign correct role, default user configured is username: 'imanage' and password: 'imanage'
* Login to the application on http://127.0.0.1:8080

### Docker Image

* Create docker image using 'docker build -t imanage/web-stack .'
* Run docker image using docker run --publish 8080:8080  imanage/web-stack