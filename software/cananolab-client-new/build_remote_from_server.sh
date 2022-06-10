#!/bin/sh
# this is used only to build and deploy directly from server
# not used in jenkins
rm -rf '/local/home/wildfly8a/cananolab_new_client'
ng build --base-href '.' --configuration 'production' --output-path '/local/home/wildfly8a/cananolab_new_client'
cd '/local/home/wildfly8a/cananolab_new_client'
jar -cvf cananolab_new_client.war *
