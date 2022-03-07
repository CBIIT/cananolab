caNanoLab Docker
1. Environment variables for db user, db password and db url(nano_user,nano_password, nano_db) must be setup. Need to obtain these from person with knowledge
2. If you want to use a volume to mount a directory inside the container to a directory on the host, change the docker-compose.yml file and point the directory to the directory of your choise. It is the first parameter. In this case: /Users/einolfs/development/cananolabvolume. Change to something else.
3. Run ./start.sh
4. Can be used with docker volume. Refer to docker documentation.

Restarting docker after code change:
1.  Simply run ./start.sh or the following: docker-compose stop, docker-compose up
