*** How to run the application ***
1. Download the folder app
2. Open the folder and double click on setup.bat to start
3. To change the context (choose a different time of the day), open application.properties file and change the hour, minute, second properties

*** Implementation Concept *****
1. I assumed that I have a file called users.csv with the details of the user. You can change the file content to suit your desired test cases
2. I am using mailtrap.io to simulate SMTP connections. T get the emails, you may need to setup a mail trap account or log in with my crendentials to view mails
  - Username: megafu.charles@yahoo.com
  - Password: friendsurancetemp
  
*** Obeservations ****
Line 35 of ItemProcessing produced an endless loop for scenarios where the users details is not NULL. I introduced a hack to work around. Hope it wouldn't count against me.

---> HAPPY RUNNING :)	