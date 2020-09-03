# PISA
Greetings! This project, called PISA, which means "private information sharing application", as you might guess from the name, is intended for private correspondence between two users.

The application runs in the console. Next, following the instructions in the program, you need to enter certain data (Such as the IP address of your interlocutor).

##### Attention! The application runs on sockets. Therefore, there are two scenarios for using this program. The first is using it to communicate in a local network. The second is the use on the World Wide Web. In both cases, you must have ports open.

To find out each other's IP addresses, you and your interlocutor need to use a third-party communication channel.

### About the application

When you turn on and enter the necessary information, the application creates a server where messages will be received, and a client with which you will send your messages.
Each message is encrypted with a public RSA key with a key length of 2048.
With each new conversation, new keys are created.
If one of the participants leaves the conversation, the other will be notified about this, and the application will stop working.

P.S. If you find bugs in my project - write to me about it. 
Good luck in communication
