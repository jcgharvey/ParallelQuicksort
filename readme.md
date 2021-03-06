Parallel Quicksort
==================
For the full history of our project, see the Git repository here:
https://github.com/jcgharvey/ParallelQuicksort

You can see our individual contributions here:
https://github.com/jcgharvey/ParallelQuicksort/graphs

Most of the work was undertaken using peer programming, so the number of commits
per person does not reflect their code contribution.



Comparing three parallel implementations
----------------------------------------
Implement a Quicksort algorithm (see Cormen et al., Introduction to Algorithms)
that sorts a large array of numbers. Of this relatively simple recursive
algorithm you have to implement three versions:
* Using Parallel Task
* Using OpenMP (Pyjama)
* Using threads and standard Java classes.

Getting Started
---------------
1. Clone the repo:
`git clone https://github.com/jcgharvey/ParallelQuicksort.git/`

2. Create the project and classpath files:
`cp .project.dist .project`
`cp .classpath.dist .classpath`

3. Edit .classpath and replace %project-dir% with the fully qualified path to your project, eg `/home/andrew/ParallelQuicksort`

4. In Eclipse, File -> Import -> General -> Existing projects into workspace. Navigate to the project and import it.
