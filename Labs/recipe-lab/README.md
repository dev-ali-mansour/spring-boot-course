# Testing Your Recipe Bookmark API

> **Note:** Since this environment has no open ports, these curl commands are for your reference.
> Use the test file to verify your implementation, or run them if you deploy locally.


> **A note on project structure:** In this lab, the controller talks directly to the
> repository. In production Spring Boot apps, you'd add a **service layer** in between
> (`RecipeController` → `RecipeService` → `RecipeRepository`) to keep business logic
> separate from HTTP handling. You'll build exactly that pattern in the next lab.
> For now, keeping it direct lets you focus on `@RequestParam`, `@Valid`, and
> `ResponseEntity` without extra layers getting in the way.

---

### 1. Add a Recipe (no validation)

```bash
curl -X POST http://localhost:8080/api/recipes \
  -H "Content-Type: application/json" \
  -d '{
        "title": "Mushroom Risotto",
        "category": "pasta",
        "rating": 4
      }'
```

---

### 2. Add a Recipe with Validation

```bash
curl -X POST http://localhost:8080/api/recipes/validated \
  -H "Content-Type: application/json" \
  -d '{
        "title": "Greek Salad",
        "category": "salad",
        "rating": 5
      }'
```

**Try this to see validation in action — expect a 400:**
```bash
curl -X POST http://localhost:8080/api/recipes/validated \
  -H "Content-Type: application/json" \
  -d '{
        "title": "",
        "category": "salad",
        "rating": 9
      }'
```

---

### 3. Get All Recipes

```bash
curl -X GET http://localhost:8080/api/recipes
```

---

### 4. Filter Recipes by Category

```bash
curl -X GET "http://localhost:8080/api/recipes?category=salad"
```

```bash
curl -X GET "http://localhost:8080/api/recipes?category=pasta"
```

---

### 5. Get a Recipe by ID

```bash
curl -X GET http://localhost:8080/api/recipes/1
```

**Try a missing ID — expect a 404:**
```bash
curl -X GET http://localhost:8080/api/recipes/9999
```



### 6: Filter by Rating

```bash
curl -X GET "http://localhost:8080/api/recipes?rating=5"
```

```bash
curl -X GET "http://localhost:8080/api/recipes?category=pasta&rating=4"
```

