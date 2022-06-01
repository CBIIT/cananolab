#!/bin/sh
rm -rf cananolab_new_client
ng build --base-href '.' --output-path cananolab_new_client
cd cananolab_new_client
jar -cvf cananolab_new_client.war *
