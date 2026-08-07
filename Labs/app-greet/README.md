
# Hello Banking API – Curl Commands

Use the following cURL commands to test your endpoints:

---

### GET /hello

```bash
curl -X GET http://localhost:8080/hello
````

---

### POST /hello

```bash
curl -X POST http://localhost:8080/hello \
     -H "Content-Type: text/plain" \
     -d "EmbarkX"
```
