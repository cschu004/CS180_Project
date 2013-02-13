*** You cannot run  this on a emulator. Emulators do not have google play services framework. Because this app uses your google account to login you can only run this on an actual device with a google account.


Follow these steps to import the Google Play Services Library into your Eclipse Project.

1. Right Click this project in Package Explorer, select import. Select Existing Android code into Workspace.

2. For root Directory, browse to project folder and select the google-play-services_lib folder. Click Finish.

3. Go to project propoerties. Selecct Java Build Path in left pane. Select Libraries Tab. Click add JAR. Select the google-play-services_lib -> libs -> google-play-services.jar.

4.Select Android in the left pane. In the library section select add. Select google-play-services_lib.