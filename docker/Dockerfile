# You can change this to any version of ubuntu with little consequence
FROM java:8
MAINTAINER Jay Kamat github@jgkamat.33mail.com

# This dockerimage will serve as a 'static' base for this DoCIF project

# Setup apt to be happy with no console input
ENV DEBIAN_FRONTEND noninteractive

# setup apt tools and other goodies we want
RUN apt-get update --fix-missing && apt-get -y install apt-utils wget curl iputils-ping vim-nox debconf-utils git software-properties-common sudo && apt-get clean

# set up user <this could be used for running your program from an X socket later on>
# Replace 1000 with your user / group id
RUN export uid=1000 gid=1000 && \
    mkdir -p /home/developer && \
    echo "developer:x:${uid}:${gid}:Developer,,,:/home/developer:/bin/bash" >> /etc/passwd && \
    echo "developer:x:${uid}:" >> /etc/group && \
    echo "developer ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/developer && \
    chmod 0440 /etc/sudoers.d/developer && \
    chown ${uid}:${gid} -R /home/developer

USER developer
ENV HOME /home/developer

# do everything in root's home
RUN mkdir -p /home/developer/project
WORKDIR /home/developer/project

# Add a docker init system and use it
ADD https://github.com/ohjames/smell-baron/releases/download/v0.1.0/smell-baron /bin/smell-baron
RUN sudo chown developer:developer /bin/smell-baron && sudo chmod a+x /bin/smell-baron
ENTRYPOINT ["/bin/smell-baron"]

# This image is not meant to be run directly, it has not been compiled yet!
# In addition, it does not contain any source code, only dependencies
