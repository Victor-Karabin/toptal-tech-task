## Intro
Your task is to implement a very simple Github repository viewer according to the given requirements.

### Requirements
Build an Android app written in Kotlin that will use GraphQL Github API to download and show list of repos for an arbitrary user/organization.  
For simplicity:
- user/organization can be hardcoded (for example to `toptal`)
- pagination is not a necessary feature for this application

The items on the list should display:
- title of the repository
- url of the repository

Tapping a list item should navigate to another screen with the details of the tapped item:
- title of the repository
- counts of open issues, closed issues, open PRs and closed PRs
- titles of the open issues and PRs

This doesn't need to look beautiful in terms of UI (some of the building blocks for the UI are already provided).

### Code

You're given a basic project setup to help with the implementation, but you're free to modify the provided code in any way you want.
This means you can add or removing libraries, replace DI framework, refactor or even completely remove existing code, as long as in the end the requirements are met.

Please refrain from using third-party application-wide frameworks like `uber/RIBs`, `slack/circuit` and similar.
Focus on the solution's correctness, architecture, scalability, clarity and maintainability.
Write code as if it was an MVP of an app that will eventually grow to tens of thousands lines of code.
Adding new features should be easy, and you should have an idea how to solve scaling challenges.
You can take shortcuts, but you should have a plan on how you would address them given enough time.

Take as little or as much time as you think is appropriate. 
We expect the task to take no more than 10-12 hours to implement the requirements and set a good foundation for discussing the architecture in a later stage.\
To stress this out: a fully comprehensive solution is not expected at this stage. Comments or explanations outlining potential future code development are entirely acceptable.\
The goal of this assignment is for you to present your vision for a scalable architecture. 

### Next steps

We ask you to send back the solution within a week - you can either share a Google Drive link, email an archive or send it to any of the Android team members on Kotlin Slack :)
The next stage will be a live coding exercise — we will ask you to make some changes to your solution like fixing a bug, improving a particular area, or implementing a new requirement.

### Hints
- You don't need to be very familiar with GraphQL, the provided code contains the basic setup already
- You can use the [Github API Explorer](https://developer.github.com/v4/explorer/) to play GraphQL and quickly iterate over the queries
- [JS GraphQL](https://plugins.jetbrains.com/plugin/8097-js-graphql) plugin can be used to highlight and autocomplete the queries in IJ-based IDEs
- Feel free to reach out if you have any questions
- `com.gradleup.auto.manifest` and `buildSrc` plugins will help you add new modules quickly

Good luck!
