# How to supply arguments while running with Maven?
When configuring the `Maven Build...` target, Add a parameter with the name `exec.args` and the arguments you want as a value.

# Running the client
Simply run the goal ``exec:java@client`` with two arguments: the host and the port.

# Running the server
Run the goal ``exec:java@server`` with one argument: the port to listen to.