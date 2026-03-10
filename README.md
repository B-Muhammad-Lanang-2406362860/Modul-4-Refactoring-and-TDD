# 4️⃣ Modul 4 - Coding Standard

## 📋 Reflection 01

1. **Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.**

**Answer:** 
- I think unit test is super useful especially when it comes to refactoring, because now I have a "safety net" and ensure that the refactor that I'm doing didn't just breaks anything. I can just easily refactor my code and go check the unit test, if it failed then I should fix the refactor code, if it's success, then I know I'm doing fine. Unit test give me instant feedback if I change certain things. It also restructure my workflow of coding. I usually just implement the logic first and make unit test after. But now, it's easier to make unit test first, because then I can implement the logic BASED ON the unit test. The unit test acts like a framework. I got a "guide" for what type should I return, what should I do if this element is not found, etc. The overall implementation got slightly faster with minimum confussion.  

2. **You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.**

**Answer:** 
First let's see what F.I.R.S.T principle is all about: 

- **F**ast -> My unit test DOES execute quickly (under two seconds). This allows me to run it constantly and give me instant feedback for my implementation and refactoring.

- **I**ndependent -> My unit test didn't rely on each other. I can run them in any order.

- **R**epeatable -> My unit test run successfully on my local environment and on the github workflow ci/cd. 

- **S**elf-validating -> My unit test can automatically detect success or failure with boolean outcome (using assertEqual, assertNull, assertTrue, etc.). 

- **T**imely -> My unit test is written before I implement the logic code. Therefore I can easily follow the Red-Green-Refactor TDD cycle.

Hence, I think my tests have successfully followed F.I.R.S.T principle. Only thing I'd do next time I create more tests is, I will make the skeleton first before making the unit test to prevent constant red dotted line appearing on my syntax (it's the error checking from my editor).

## 📋 Reflection 02

TODO