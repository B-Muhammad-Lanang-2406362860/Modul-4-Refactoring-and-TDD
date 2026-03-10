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

## 📋 Reflection 02 (Bonus: Peer Code Review)
### You can access my Pull Request on my friends repository (Zita) [here](https://github.com/B-Zita-Nayra-Ardini-2406404913/Modul-4-Refactoring-and-TDD/pull/2). 

1. **Explain what you think about your partner’s code? Are there any aspects that are still lacking
from your partner’s code?**
**Answer:**
- I think my partner’s code is already quite clear and the main logic is understandable. The service methods mostly show what the program is trying to do. But I feel there are still some parts that can be improved a bit, especially about how the order object is updated and how failure cases are handled. Some behaviors in the code are not very explicit, so in the future it might be a bit confusing if someone else reads or modifies the code.

2. **What did you do to contribute to your partner’s code?**
**Answer:**
- For my contribution, I mostly tried to review the service logic and point out some parts that could be safer or clearer. I also suggested small refactor ideas to fix the code smells and don't forget to run the test suite to ensure behavior stays correct after we change the implementation. 

3. **What code smells did you find on your partner’s code?**
**Answer:**
- One code smell I noticed is inconsistent update logic in the update method. The method creates a new Order object with the new status, but the code actually saves the old object and then returns the new one. This can make the returned data different from what is stored in the database, which can be risky and confusing when debugging. Another smell is in createOrder in OrderServiceImpl.java line 16. The method returns null when a duplicate order already exists. I think this is a bit unclear because every caller now must remember to check for null, and if someone forgets it might cause NullPointerException.

4. **What refactoring steps did you suggest and execute to fix those smells?**
- To improve this, I suggested some simple refactoring steps. For the update logic, the service should load the order once, then update the status directly on that same object, and then save and return the same instance. This way the saved data and returned data are always consistent. For the duplicate order case, I suggested not returning null, but instead using a clearer failure handling like throwing a domain exception or maybe using Optional.
