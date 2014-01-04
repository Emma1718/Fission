#!/bin/sh
schedtool -a 1 -n 0  -e java -Xmx6144m -cp SnortCLI/target/SnortCLI-1.0-SNAPSHOT.jar:SnortEngine/target/engine-1.0-SNAPSHOT.jar:BoardGame/target/BoardGame-1.0-SNAPSHOT.jar:BoardGameEngine/target/BoardGameEngine-1.0-SNAPSHOT.jar:Cleave/target/Cleave-1.0-SNAPSHOT.jar put.ai.snort.snortcli.App $*
