# fortnight

This is an automation testing project.

automated website- spicejet.com
automation tool and frameworks used- selenium, maven, TestNG
automated browser/driver used- chrome
 
 
Functionalities:
1. Scheduled testcases using TestNG @priority
2. Maven build and POM updated with all dependencies
3. Log reporter at every step
4. Screencapture (Ashot) which compares testcase screenshots(captured after every changes on page) with baseline screenshots.
5. Screen Recording of full test execution using Monte screen recorder.
6. Stopwatch that calculates execution time of each test case.
7. No hardcoding.
8. Use of Soft Assertion and implicit wait.


Testcases:
1. Tab order checked
2. Count of total checkboxes, radio buttons, tabs, and links(valid, invalid, mail and total) on homepage.
3. Automating the homepage by selecting all options on homepage and checking the visibility of all fields.
-handling static dynamic dropdowns
-handling calendar UI
-Validating if UI Elements are disabled or enabled with Attributes
-handling checkboxes
4. Member login attempts and handling popups.
- characters functionality checked


How to run?
Using IDE- Run spicejettestng.xml or pom.xml in project folder
Using CMD- mvn test


Full Automation is performed on single run and with one time browser invokation.


Thanks,
Happy Testing!!
