#!/bin/bash
cd ~/Minecraft/forge/mcp/
./recompile.sh
./reobfuscate_srg.sh
cd reobf/minecraft/
jar -cfM FurnitureMod.jar yafm/
mv FurnitureMod.jar ../../../source/FurnitureMod/releases/
cd ../../../source/FurnitureMod/resource/
jar -ufM ../releases/FurnitureMod.jar *
cd ~
