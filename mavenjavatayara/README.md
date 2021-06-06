This is an automation testing project.

automated website- tayara.tn (Non Europian users - use VPN)

automation tool and frameworks used- selenium, maven, TestNG

automated browser/driver used- chrome

Task:
1. Data Extraction
2. Layout and position Testing 

Functionalities:

1. Use of TestNG annotations
2. Maven build and POM updated with all dependencies
3. Screen Recording of full test execution using Monte screen recorder.
4. Stopwatch that calculates execution time of each test case.
5. No hardcoding.
6. Use of Soft Assertion and implicit wait.

Testcases:

1. Extraction:
- Extracted total number of ads and details of each ad by clicking 10 times on "load more" button dynamically. 

2. Layout
- position and size of "logo" checked
- fontsize, color, and fontfamily of "Toute la Tunisie" checked


Challenges:
In position testing, If you set the browser window size to maximum and try to run the code in different desktop then in most cases the testing will tend to fail 
as the resolution of different desktops may vary and you will get different position of elements on different devices.
Possible solution is to set the browser window to the default size (1024,768).

How to run:

Using IDE- Run tayaratestng.xml or pom.xml in project folder
Using CMD- mvn test
