Notes:

- I've approached this as if it were a real project. So I've kept to the specification.
- I've used TDD to reduce the risk of regressions during future
  work/refactor [insert TDD opinion here]
- No DI as I assume the proejct would already have it. I have [insert DI opinion here]
- Code style is vague as I find almost every project has it's own requirements
- I've avoided mocking frameworks (e.g Mockito, Roboelectric, MockWebServer) as the requirements
  were simple
- Security. This is a public api with basic auth, so I haven't used security. I would never ship
  with credentials but for the sake of this test.
- Errors. I've made no attempt to make errors user friendly. In an ideal world error behaviour
  should be treated as seriously as the main requirements.
- Persistence. Using a DB would be excessive for this usecase. Although it would make the UI more
  responsive. I've gone with just VM
- Prevent writing the VM state from outside the VM
- Started with component tests, then the integration tests
- I like to create helper functions for tests, like Jake Wharton's Robot pattern
- Image cache
- Nothing defencive done to prevent clicks
- Error handling
- String resources and URLs
- Pass data by ID. It can be slower but it forces you to be more robust in the end
- Theming and styling. I didn't get too into it. I rely heavily on designers. Reuse styles
- Persistence (not doing because of error handling)
- Repositories
