# TicketBeast API

[![CI workflow](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/ci.yml/badge.svg)](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/ci.yml)
[![Release workflow](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/release.yml/badge.svg)](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/release.yml)
[![semantic-release: conventional-commits](https://img.shields.io/badge/semantic--release-conventionalcommits-e10079?logo=semantic-release)](https://github.com/semantic-release/semantic-release)

REST API for TicketBeast from [Test Driven Laravel](https://testdrivenlaravel.com/).

In order to run this application you'll need a [Stripe API Key](https://stripe.com/docs/keys).
Please add your key to the file `src/main/resources/adapters.properties` using the command below

```bash
echo "stripe.apiKey=YOUR_STRIPE_API_KEY" >> src/main/resources/adapters.properties
```

You'll also need a salt value for the codes generated for tickets.

```bash
echo "hashids.salt=YOUR_SALT" >> src/main/resources/adapters.properties
```

You can access the Swagger UI for this application in http://localhost:8080/swagger-ui/index.html?/v3/api-docs/swagger-config

If you want to test the endpoint to purchase tickets, you'll need to generate a Stripe test token using the following command.

```shell
curl https://api.stripe.com/v1/tokens \
  -u YOUR_STRIPE_API_KEY: \
  -d "card[number]"=4242424242424242 \
  -d "card[exp_month]"=8 \
  -d "card[exp_year]"=2023 \
  -d "card[cvc]"=314
```

The test token is in the `id` property in the response.

Browse the [OpenAPI Specification](https://montealegreluis.com/ticketbeast-api/) for more details.
