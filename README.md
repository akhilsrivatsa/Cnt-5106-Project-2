# Cnt-5106-Project-1

Akhil Srivatsa - 80826297

The project has been implemented in Java. I've used Intellij Idea to run the project. 

Steps to Run the project - 

1) Build and run Server.java
2) Provide the port that on which the server has to run. The port can be supplied from the terminal using the command "ftpClient 5016".
  Here 5016 is the port number. Once the port number is provided, you should see a message saying that the server has been started.
3)  Build and run Client.java.
4) Inorder to upload the file, Provide the following command - "upload filename"
5) Inorder to  download hte file, Provide the following command - "get filename"
6) The input file directories are hardcoded in the client.Java file on line No 35 and 62.
7) The output file directories are hardcoded in Server.java file on line No 38 and 56.
8) The directories need to be updated as per user's local file directories.

File Structure - 


1) Client.java

The client.java file is present at the client side. It accepts inputs from the client to upload and download the file. 
The accepted commands are "get filename" and "upload filename". Any other commands will be termed invalid, and the user will be prompted 
to enter the command again.

2) Server.java

The server.java file is used to initialize the server. The port can be provided through the terminal. The server accepts command
in the format "ftpclient 5016". Any other command will be termed invalid and the system will be terminated.

Working details - 

Every time a new client connects to the server, a new thread will be spawned by the server. All requests by the client will be taken care by the thread allocated to the client.


![img.png](img.png)

![img_1.png](img_1.png)