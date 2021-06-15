# JaaS token generator
## Overview
JaaS token generator is an unauthenticated Spring Boot service that allows JaaS customers to easily deploy and generate [JaaS](https://jaas.8x8.vc/) JWT tokens.
## Deploy to Heroku steps
1. Create a [Heroku](https://signup.heroku.com/login) account if you don't already have one.
2. Generate an [api key](https://developer.8x8.com/jaas/docs/jaas-console-api-keys) from the admin console and download both keys.
3. Click the deploy button that will redirect you to Heroku's deployment page.
4. Fill the input fields
    1. Add any name for your app.
    2. Add public key id from the JaaS admin console
    3. Add the private key that was generated at step 2.
5. Go to https://name-of-your-app.herokuapp.com/token-generator/swagger-ui.html to view the API.

## [![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/8x8/jaas-heroku.git)