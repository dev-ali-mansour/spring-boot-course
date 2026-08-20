**README.md**

**Testing Endpoints**

Experience the problem first:
```bash
curl -s http://localhost:8080/api/employees | cat
```

Basic pagination:
```bash
curl -s "http://localhost:8080/api/employees?page=0&size=5" | cat
curl -s "http://localhost:8080/api/employees?page=1&size=5" | cat
curl -s "http://localhost:8080/api/employees?page=4&size=5" | cat
```

Sort by last name ascending:
```bash
curl -s "http://localhost:8080/api/employees?sort=lastName,asc" | cat
```

Sort by last name descending:
```bash
curl -s "http://localhost:8080/api/employees?sort=lastName,desc" | cat
```

Sort by salary descending:
```bash
curl -s "http://localhost:8080/api/employees?sort=salary,desc" | cat
```

Sort by department:
```bash
curl -s "http://localhost:8080/api/employees?sort=department,asc" | cat
```

Pagination and sorting combined:
```bash
curl -s "http://localhost:8080/api/employees?page=0&size=5&sort=lastName,asc" | cat
```

Filter by department:
```bash
curl -s "http://localhost:8080/api/employees?department=Engineering" | cat
```

Filter by department with sorting:
```bash
curl -s "http://localhost:8080/api/employees?department=Legal&sort=salary,desc" | cat
```

Filter by department with pagination:
```bash
curl -s "http://localhost:8080/api/employees?department=HR&page=0&size=3" | cat
```

Sort by multiple fields:
```bash
curl -s "http://localhost:8080/api/employees?sort=department,asc&sort=lastName,asc" | cat
```
```

```