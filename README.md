# Java Farming Game

A university programming project developed in Java. The game is a tile-based farming simulation where the player can move around the map, collect and use tools, prepare soil, plant and harvest crops, interact with the environment, and earn/spend in-game currency.

## Project overview

This project was completed as part of my first-year Fundamentals of Programming coursework. A starter framework was provided for the coursework, which I extended with the gameplay logic and features described below.

## Features I implemented

- Grid-based player movement with boundary checking
- Randomised dirt/farm area generation
- Tool collection and tool-specific interactions
- Tilling soil and sowing seeds
- Crop growth and harvesting
- In-game currency rewards for harvested crops
- Trees and rocks that require the correct tools to clear
- Tool durability
- Pest spawning and movement towards crops
- Weather states that affect crop behaviour
- Watering-can interactions
- A one-time NPC interaction
- Signpost interaction
- A simple shop using the existing currency and tool systems

## Technologies

- Java
- Java Swing / AWT
- Object-oriented programming
- 2D arrays and tile-based game logic
- Event-driven keyboard input
- Randomised game behaviour

## Project structure

```text
java-farming-game/
├── assets/          # Game sprites and tile images
├── src/             # Java source code
├── nbproject/       # NetBeans project configuration
├── build.xml        # Ant build file
└── README.md
```

## Running the project

The project is configured as a NetBeans/Ant Java project.

### NetBeans

1. Clone or download the repository.
2. Open the project folder in Apache NetBeans.
3. Ensure a compatible JDK is selected for the project.
4. Run `uk.ac.bradford.farmgame.Launcher`.

The game loads its image assets from the `assets` directory, so run it with the repository root as the working directory.

## What I learned

This project helped me practise building a larger program from multiple interacting classes rather than a single script. I worked with object-oriented programming, state changes, movement and boundary logic, arrays, random events, user input, game mechanics, and connecting new features to an existing codebase.

## Academic note

This repository is a portfolio copy of completed university coursework. The original coursework included a university-provided starter framework, which remains attributed in the source files. The gameplay extensions and coursework tasks I completed are presented here to demonstrate my programming development.
