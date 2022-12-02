### Overview
I started implementing my best practices but had to rush a bit once I was into the FileExplorer to make sure I finished on time.

I have added the ability to sign in, view images and folders, and create a new folder.

For the most part, I have treated this like a real greenfield project, and have made decisions to prioritise future refactoring/extensibility.

I wasn't able to do file deleting or image loading. Had I gotten that far I would've implemented image loading using retrofit MultipartBody requests. I would use the native image picker for simplicity.

Below I've touched on some specific points and decisions I've made.

#### AAC
I thought this would be a good opportunity to try going all in with AAC as I've mostly worked on projects that are hybrids of the AAC and the more traditional tools.

#### TDD
I test-drove the sign-up flow, starting with the component tests and working down to the API service. I'm a believer that TDD helps to keep code maintainable so I always advocate for it, that being said, I'm flexible if a project has a stance against TDD.

#### DI
Given the few dependencies required implementing DI seemed overkill. I have kept the dependency graph clean though, so it would be easy to implement in the future. I would use Hilt for this project as I think it's the best option when use AAC. I didn't use any mocking frameworks (e.g. Mockito, Roboelectric) as there was only a limited amount of mocking needed for the tests

#### Authentication
Due to time constraints, I had to put a hack in to share the authentication with Glide. Ideally, I would've done this using Okhttp interceptors. I think it's useful to use Glide for image loading over manually fetching the data because of all the tools Glide (or similar libraries) comes with.

#### Errors & Robustness
I've been very basic with feedback for errors or preventing bad user behaviour (like double clicks). It would be more user-friendly with more substantial error handling/messaging.

#### Themeing & Styling
I've only applied very basic styles and animations. I find working too hard on UI without a designer can waste a lot of time. Ideally, a good design spec would come before I try and make things pixel-perfect.

#### Persistence, Repositories etc
Similar to the DI, the timescale was too small to implement. In an ideal world, we would be able to store the user's authentication securely on the device (using the Keystore), and we could persist the directories to prevent multiple calls for the same data (Either just in SharedPreferences or with a more complete solution using Room)
