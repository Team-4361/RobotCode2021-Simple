# Team 4361 - ROXBOTIX
<i>
Note: you can ignore everything I'm about to say. I only have a year of
experience on the team and am probably not up-to-date on a couple of
best practices.
</i>

## About
I created this repository for one purpose - standardization. Typically, in
engineering environments, efforts are made to standardize as much as possible.
This is done to alleviate headaches caused by incompatible standards. Imagine
if every single screw you have is an entirely different size? Although
programming and screw size aren't exactly analogous, it's a good starting point
for the rest of my reasoning behind a single-repository approach.
- Standardization. Put simply, having so many repositories can be a total
  nightmare, especially for smaller groups of people. Collaboration almost feels
  discouraged by separate repositories. In a single-repository world, this
  issue would be largely alleviated. I can simply work on the robot, not
  RobotCode2021 or FRC-4361-FinalCode-2020.
- Smoother learning curve. Newer team members without any FRC programming
  experience are undeniably going to be intimidated when faced with a single
  objective: code the robot, from scratch. By moving to a single repository,
  it's made significantly easier to learn from previous code. Creating a new
  repository for each year feels incredibly counter-productive - it's as if all
  the progress made over the last year has been reset. While yes, the code is
  still available on GitHub, trying to import code from a past year gave me the
  following issues.
  - Missing libraries.
  - Missing vendor dependencies.
  - Outdated JDK.
  - Outdated version of Gradle.
  - Missing wpilibj.
- Easier to get more done. Storing code from anything robotics-related in a
  single repository means that I can simply look at robotics code instead of
  having to search through three dozen repositories for what I want.
- Better documentation. Think about it - is it easier to document a decade of
  code progression and libraries over the span of a decade or a single build
  season? By using a single repository, documentation can carry over from
  year-to-year while still being accessible. If I wanted to use a piece of
  code written three years prior to my joining of the team, but it wasn't
  documented, I'd have a hard time figuring out what to do. If all the code had
  been stored in a single repository, it would be significantly easier to
  (a) locate the code
  (b) find documentation or examples
  (c) implement the code.
  
I've presented the argument for single-repository standardization in hopes that
future FRC members without FRC coding experience, such as myself, are able to
quickly pick up on what's going on and what they need to do. In addition to
improving the quality of life for developers, especially those without extensive
FRC programming experience, single-repository standardization makes 
collaboration across several sub-teams, sub-systems, and sub-projects much
easier. Although ultimately the decision isn't up to me, I do believe that
making such a monumental change in the way our programming section works would
be beneficial to not only the developers tasked with writing code, but the
entirety of ROXBOTIX as a whole.

## Seasons
Each season has its own package dedicated to its code. For example:
- 2020's season is `frc.team4361.season2020`.
- 2021's season is `frc.team4361.season2021`.
- 2022's season is `frc.team4361.season2022`.

Code that applies only to a single given season should be in that season's
package. Examples of code that would only apply exclusively to a single season
include operation modes, autonomous modes, hardware configurations, and certain
specialized subsystems. However, any code that can be re-used should go into
a library - this way, the amount of time required to program is significantly
decreased; as already having code takes a lot less time than having to type it.

## Project Upgrades
There's three different methods I can think of for importing a project from
a previous year into the current year. This situation occurs between seasons.
Changes are made to the software on the RoboRIO, etc. 
- Creating a new WPILIB project, linking it with this GitHub repo, and importing
  code and vendordeps from previous years.
- Manually upgrading all the content that has changed.
- Not updating code. I'm not sure if this is in compliance with FRC rules and
  regulations, but not updating code would be the most optimal solution.
  Unless there is a change that's made that benefits the robot, there's no
  point in potentially breaking tons of code. In the words of someone much
  wiser than myself - "if it ain't broke, don't fix it."

## Team Library Development
In case you couldn't yet tell, this project has a ton of different libraries.
The entire purpose of a library is to prevent the need for re-writing code.
Why would I want to write a method to do X, Y, and Z, when I already have one?
During the early stages of the 2021 season, I realized that there was an
astounding lack of code written for commonly situations - the libraries that
were present either (a) didn't work or (b) accomplished comparatively little.
To resolve this issue, I created the elibs2 library, which is essentially all
the code that could be re-used. In the name of collaboration and efficiency,
any 4361 team member is free to use, modify, change, etc - any of the code
in this library. In fact, I'd suggest that you add to it - if every single
year, 3 people added 10 different functionalities, it wouldn't take long before
a library complete with enough FRC code to last a lifetime was built. This would
indubitably speed up programming.

## Style
<i>
Note: I would strongly advise that these style guidelines are read and checked before
they're in any way enforced.
</i>
Coding style, and sharing one, is incredibly important for a team. Although
minor things such as IDE don't matter, as all an IDE is a fancy text editor,
more major things, such as coding style, significantly impact how a team works.
There's two major components of style - formatting and structure.

### Formatting

#### Java
Code should generally adhere to the Google Java Style Guide, which is available
[here](https://google.github.io/styleguide/javaguide.html). Because ROXBOTIX
is a high school team, and not a multi-billion dollar tech giant, it's
unrealistic to expect all team members to follow exactly the same style guide
to a tee. However, the following is expected as a minimum for style.
- Brackets don't get their own new line.
- Comments (// text) are separated as shown, and not like this: (//text)
- JavaDocs are present for every PUBLIC method, class, field, or enum. Although
  not required for private and protected objects, it is very strongly suggested
  that every single functionality you code is documented so that someone who's
  never read your code before can understand it.
- `@author` tags are present in the heading of every class. Knowing who wrote
  what is hugely important to team-work. Files that have multiple authors can
  have multiple author tags. You're considered an author of a file if you've
  made a significant impact on the file's functionality. Correcting a spelling
  mistake in a JavaDoc, for example, is not considered authoring the file.

### Structure
Generally, code in this project follows a group-by-purpose layout. Code that
has the purpose of modifying controller values, for example, would go in a
single package. Contrary to the group-by-layer layout, where elements are
grouped based on what level of the application they're on, group-by-purpose
provides a fantastic way of making code easy to find and easy to read. It's
much easier to find controller code if you find a package named "controller"
than if you have to search through every package you can find.

Abstraction is one of the core components of many of the libraries in this
project. elibs2, for example, extracts nearly everything - from physical
components to software components and everything in between. This is done
so that code is incredibly easy to refactor and repurpose. Therefore, any
interaction with the robot's physical components should be handled through
some form of an abstraction, allowing future developers to easily modify,
fix, or improve previous code.

## Libraries
For the sake of making everything well-documented and standardized, a list
of all the libraries this project depends on should go here.
- `elibs2` by Team 4361 (source)
- `elibs3` by Team 4361 (source)
- `libraries` by Team 4361 (source)
- `Reflections` by ronmamo (build.gradle)
- `PathfindingCore` by Xavier Guzman (libs)
- `Pathfinder` by Colin Robertson (libs)

## Documentation
Everything should be documented, and I do mean everything. Although documenting
code can certainly be a hassle, it saves other developers hours upon hours of
their life. If you write a method, you should document it. If you come across
a method that hasn't been documented, you should document it. In addition to
documenting code in the code itself, documentation can be done externally.

The folder `/docs/` contains a portal for all sorts of documentation. If you're
learning a new library that the robot will depend on, you should document
your learning process along the way. It's as simple as copy-pasting a
StackOverflow link into a Markdown file, and would you look at that - future
developers (or even current ones) don't have to go through the same learning
curve as you. 