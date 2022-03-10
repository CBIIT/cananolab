#!/bin/sh
rm -rf /tmp/cananolab_new_client
ng build --base-href '.' --output-path /tmp/cananolab_new_client
cd /tmp/cananolab_new_client
jar -cvf cananolab_new_client.war *






cp cananolab_new_client.war /opt/wildfly-8.2.1.Final/standalone/deployments
