# UltimateSmashTracker
A tool for tracking your smash bros games amongst your group of friends

## Docker
The `SSK` and `tokenExpirySeconds` environment variables are set in the Dockerfile

To build docker image:
* `docker build -t smash-tracker .`

To run docker image detached:
* `docker run -p 4000:8080 -td smash-tracker`

To attach to the running docker image:
* `docker ps` to get running docker images
* copy the `CONTAINER ID` value
* docker attach `<CONTAINER ID>`

To detach from a runner docker image without stopping it:
* `CTRL + c`
  > This would normally send the SIGKILL command to the container, but because we started it with the -t (tty) option, it simply detaches and leaves it running

To stop the running docker container:
* `docker ps` to get running docker images
* copy the `CONTAINER ID` value
* docker kill `<CONTAINER ID>`