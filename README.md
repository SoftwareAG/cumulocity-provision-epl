# Cumulocity microservice to provision epl

This microservice provides a `POST` endpoint `provision` to deploy an EPL model from the enterprise tenant to a subscribed target tenant.
A sample `POST` request looks as follows:
```
{
    "model": "High Frequency Availability",
    "targetTenant": "YOUR_TARGET_TENANT_ID"
}
```
The result returns the id of the generated EPL file:
```
[
    {
        "tenant":  "YOUR_TARGET_TENANT_ID",
        "model": "High Frequency Availability",
        "id": "46291137"
    }
]
```

------------------------------

These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.
