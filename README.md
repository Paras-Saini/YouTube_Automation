# YouTube_Automation

Automated testing of YouTube's sidebar navigation, movie/music/news sections using Java, Selenium WebDriver, and TestNG. Includes custom wrapper methods and multiple end-to-end test cases covering navigation, soft assertions, data extraction, and validations.

Test Cases:

    testCase01:
        1. Navigate to YouTube.com.
        2. Assert that the current URL is correct.
        3. Click on the "About" link at the bottom of the sidebar.
        4. Print the message displayed on the screen.

    testCase02:
       1. Go to the "Movies" tab.
       2. In the "Top Selling" section, scroll completely to the extreme right.
       3. Apply a Soft Assert to check if the movie is marked with an "A" (Adult/Mature) rating.
       4. Apply a Soft Assert to verify the movie belongs to a valid category (e.g., "Comedy", "Animation", "Drama").

    testCase03:
       1. Go to the "Music" tab.
       2. In the first section, scroll completely to the right.
       3. Print the name of the playlist appearing at the extreme right.
       4. Apply a Soft Assert that the number of tracks listed is less than or equal to 50.

    testCase04:
       1. Go to the "News" tab.
       2. Print the title and body content of the first 3 “Latest News Posts.”
       3. Print the total sum of likes across these 3 posts (treat missing likes as 0).

How to Run
1.Clone the repository.
2.Open in your IDE (IntelliJ/Eclipse/VSCode).
3.Make sure all dependencies (Selenium, TestNG) are installed.
4.Run the TestCases.java as a TestNG test suite.
