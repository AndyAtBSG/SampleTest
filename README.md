Notes:
- I've approached this as if it were a real project. So I've kept to the specification.
- I've used TDD to reduce the risk of regressions during future work/refactor [insert TDD opinion here]
- No DI as I assume the proejct would already have it. I have [insert DI opinion here]
- Code style is vague as I find almost every project has it's own requirements
- I've avoided mocking frameworks (e.g Mockito, Roboelectric, MockWebServer) as the requirements were simple
- Security. This is a public api with basic auth, so I haven't used security. I would never ship with credentials but for the sake of this test.
