# Cumulocity microservice to provision epl from enterprise tenant to subscribed target tenant

This microservice provides a `POST` endpoint `provision` to deploy an EPL model from the enterprise tenant to a subscribed target tenant.
A sample `POST` request looks as follows:
```
{
    "model": "High Frequency Availability",
    "targetTenant": "YOUT_TARGET_TENANT_ID"
}
```
The result returns the id of the generated EPL file:
```
[
    {
        "tenant":  "YOUT_TARGET_TENANT_ID",
        "model": "High Frequency Availability",
        "id": "46291137"
    }
]
```