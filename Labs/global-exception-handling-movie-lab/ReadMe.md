Yes. Here it is:

```markdown
# Tame the Chaos: Global Exception Handling with a Movie Ratings API

The movie discovery platform needs a robust error handling layer. Right now the API
returns ugly 500 stack traces when something goes wrong — the frontend team cannot
work with that. All endpoints and services are already built and working. Your job
is to implement the GlobalExceptionHandler so every failure case returns a clean,
consistent JSON response.

**What's already provided:**

- `Movie.java` and `Rating.java` — models with validation annotations
- `MovieRepository.java` and `RatingRepository.java` — JPA repositories, ready to use
- `MovieService.java` and `RatingService.java` — fully implemented service layer
- `MovieController.java` and `RatingController.java` — fully implemented endpoints
- `MovieNotFoundException.java` and `DuplicateRatingException.java` — custom exceptions, already being thrown by the services
- `ErrorResponse.java` — response wrapper with `status` and `message` fields, ready to use
- `GlobalExceptionHandler.java` — class exists with `@RestControllerAdvice`, handlers are empty — this is where you work
- `DataLoader.java` — seeds movies and a duplicate reviewer scenario on startup so you can test without setting anything up

```bash
curl -s http://localhost:8080/api/movies/9999 | cat
```

Add a movie with blank title — expect 400:
```bash
curl -s -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{"title": "", "genre": "sci-fi"}' | cat
```

Rate with invalid stars — expect 400:
```bash
curl -s -X POST http://localhost:8080/api/movies/1/ratings \
  -H "Content-Type: application/json" \
  -d '{"stars": 9, "reviewer": "Charlie"}' | cat
```

Rate a non-existent movie — expect 404:
```bash
curl -s -X POST http://localhost:8080/api/movies/9999/ratings \
  -H "Content-Type: application/json" \
  -d '{"stars": 3, "reviewer": "Dave"}' | cat
```

Duplicate reviewer — expect 409 (Alice is already seeded in DataLoader):
```bash
curl -s -X POST http://localhost:8080/api/movies/1/ratings \
  -H "Content-Type: application/json" \
  -d '{"stars": 3, "reviewer": "Alice"}' | cat
```

Add a movie:
```bash
curl -s -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{"title": "The Matrix", "genre": "sci-fi"}' | cat
```

Get all movies:
```bash
curl -s http://localhost:8080/api/movies | cat
```

Get all ratings for a movie:
```bash
curl -s http://localhost:8080/api/movies/1/ratings | cat
```

Stretch — add same movie title twice — expect 409:
```bash
curl -s -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{"title": "Inception", "genre": "sci-fi"}' | cat
```

Average rating for a movie:
```bash
curl -s http://localhost:8080/api/movies/1/ratings/average | cat
```

Average for movie with no ratings:
```bash
curl -s http://localhost:8080/api/movies/3/ratings/average | cat
```
```