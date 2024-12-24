# 🚀 Graph Algorithms Project

##📚 Table of Contents

1. 📜 Overview

2.✨ Features

3. 🛠️ How to Run the Project

- 📋 Prerequisites

- 📖 Steps to Run

- ▶️ Execution

4. 📁 Input Files

- 📄 input1.txt

- 📄 input2.txt

5. 📘 Algorithms in Detail

- 🔹 Prim's Algorithm

- 🔸 Kruskal's Algorithm

- 🔹 Dijkstra's Algorithm

6. 📂 Files in the Repository

##📜 Overview

This project showcases the implementation of three essential graph algorithms:

- 🟢 Prim's Algorithm: Computes the Minimum Spanning Tree (MST) using an unordered minimum-priority queue.

- 🔵 Kruskal's Algorithm: Finds the MST using the Union-Find data structure.

- 🔴 Dijkstra's Algorithm: Calculates the shortest paths from a single source vertex using a priority queue.

###📌 User Interaction: The program allows users to select an algorithm through a menu and processes graph data stored in external files.

##✨ Features

-  Reads graph data from input files (input1.txt and input2.txt).

-  Implements three fundamental algorithms in graph theory.

-  Displays results (edges, weights) and execution times.

-  Supports undirected and weighted graphs.

##🛠️ How to Run the Project

###📋 Prerequisites

-  Java Development Kit (JDK) installed.

-  A Java IDE (e.g., IntelliJ, Eclipse) or a terminal.

###📖 Steps to Run

 1. Clone or download this repository to your local machine.

 2. Place the input files (input1.txt, input2.txt) in the same directory as the Java source code.

 3. Compile and run the cpcs324_group10_pt2.java file.

###▶️ Execution

-  When prompted, select the desired algorithm:
1️⃣ Prim's Algorithm2️⃣ Kruskal's Algorithm3️⃣ Dijkstra's Algorithm

- Enter any additional input if prompted.

- Results (edges, weights, execution time) will be displayed in the console.

##📁 Input Files

###📄 input1.txt

Contains graph data for Prim's and Kruskal's algorithms.Format:

- Number of vertices

- Number of edges

- Edge data: source destination weight

Example:

6
10
0 1 3
0 4 6
...

###📄 input2.txt

Contains graph data for Dijkstra's algorithm.Format: Same as input1.txt.

##📘 Algorithms in Detail

###🔹 Prim's Algorithm

Finds the MST by starting from a single vertex and expanding the tree with the smallest edge at each step.

###🔸 Kruskal's Algorithm

Finds the MST by sorting edges and adding the smallest edge to the tree, avoiding cycles.

###🔹 Dijkstra's Algorithm

Finds the shortest paths from a single source to all other vertices using a greedy approach.

##📂 Files in the Repository

1. 📝 cpcs324_group10_pt2.java: The main Java program implementing the algorithms.

2. 📄 input1.txt: Graph data for Prim's and Kruskal's algorithms.

3. 📄 input2.txt: Graph data for Dijkstra's algorithm.


####Feel free to enhance or modify this to suit your needs! 😊


