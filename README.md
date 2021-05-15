# Spring Boot + Envoy + JWT

## Realms

### Test realm

```
realm    : test
client_id: frontend
user     : test
pass     : test
```

### Admin realm

```
realm    : admin
client_id: frontend
user     : admin
pass     : admin
```

## Generating tokens

### Test user token

```bash
USER_TOKEN=$(curl -s -X POST "http://localhost:8180/auth/realms/test/protocol/openid-connect/token" \
		--fail --insecure \
		-H "Content-Type: application/x-www-form-urlencoded" \
		-d "username=test" \
		-d "password=test" \
		-d "grant_type=password" \
		-d "client_id=frontend" | jq -r '.access_token')
```

### Admin user token

```bash
ADMIN_TOKEN=$(curl -s -X POST "http://localhost:8180/auth/realms/admin/protocol/openid-connect/token" \
		--fail --insecure \
		-H "Content-Type: application/x-www-form-urlencoded" \
		-d "username=admin" \
		-d "password=admin" \
		-d "grant_type=password" \
		-d "client_id=frontend" | jq -r '.access_token')
```

## Test requests

### Test user request

```bash
curl -s -H "Authorization: Bearer $USER_TOKEN" http://localhost/hello                                     
Hello a5a82898-36a8-40b9-8c92-2fd75f8e2dc5
```

### Admin user request

```bash
curl -s -H "Authorization: Bearer $ADMIN_TOKEN" http://localhost/admin/hello                                     
Hello c1814307-e590-4af8-aa89-4b3bf326a47c
```

## Troubleshooting

### Check Envoy's clusters health

```bash
curl -s 0:9901/clusters | grep health
local_service::172.20.0.3:8080::health_flags::healthy
keycloak::172.20.0.2:8080::health_flags::healthy
```
