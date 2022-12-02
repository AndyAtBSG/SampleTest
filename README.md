### Overview
For SignIn I used TDD. Then realised I was short on time so went to implementing straight forward.

#### AAC
I thought this would be a good oppertunity to try going all in with AAC as I've mostly worked on projects that are hybrids of the AAC and the more traditional tools.

#### TDD
I test drove the sign-up flow, starting with the component tests and working down to the API service. I'm a believer that TDD helps to keep code maintainable so I always advocate for it, that being said, I'm flexible if a project has a stance against TDD.

#### DI
Given the few dependencies required implementing DI seemed overkill. I have kept the dependency graph clean though, so it would be easy to implement in the future. I would use Hilt for this project as I think it's the best option when use AAC. I didn't use any mocking frameworks (e.g. Mockito, Roboelectric) as there were only limited amount of mocking needed for the tests

#### Authentication
Due to the time constraints I had to put a hack in to share the authentication with Glide. Ideally I would've done this using Okhttp interceptors. I think it's useful to use Glide for image loading over manually fetching the data because of all the tools Glide (or similar libraries) come with.

#### Code
Notes:

- I've approached this as if it were a real project. So I've kept to the specification.

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
- Had to hack the service to share auth with Glide. Other options: Store auth somewhere with DI. Ues Okhttp interceptor on all requests
