# TicketBeast API

[![CI workflow](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/ci.yml/badge.svg)](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/ci.yml)
[![Release workflow](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/release.yml/badge.svg)](https://github.com/montealegreluis/ticketbeast-api/actions/workflows/release.yml)
[![semantic-release: conventional-commits](https://img.shields.io/badge/semantic--release-conventionalcommits-e10079?logo=semantic-release)](https://github.com/semantic-release/semantic-release)

REST API for TicketBeast from [Test Driven Laravel](https://testdrivenlaravel.com/).

In order to run this application you'll need a [Stripe API Key](https://stripe.com/docs/keys).
Please add your key to the file `src/main/resources/stripe.properties` using the command below

```bash
echo "stripe.apiKey=YOUR_STRIPE_API_KEY" > src/main/resources/stripe.properties
```

Browse the [OpenAPI Specification](https://montealegreluis.com/ticketbeast-api/) for more details.
