package com.b1n_ry.twitch_cleaner;

import me.srrapero720.waterframes.common.block.data.DisplayData;
import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.*;

public class TwitchCleaner implements ModInitializer {
    public static final Map<UUID, Set<GlobalPos>> INTERACTED_SCREENS = new HashMap<>();

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.DISCONNECT.register((packetListener, server) -> {
            ServerPlayer player = packetListener.getPlayer();
            UUID playerId = player.getUUID();
            for (GlobalPos pos : INTERACTED_SCREENS.getOrDefault(playerId, Collections.emptySet())) {
                BlockPos blockPos = pos.pos();
                Level level = server.getLevel(pos.dimension());
                if (level == null) continue;

                if (!(level.getBlockEntity(blockPos) instanceof DisplayTile displayTile)) continue;
                if (displayTile.data.uri.toString().contains("twitch.tv")) {
                    displayTile.data.uri = null;
                    displayTile.setChanged();
                }
            }
            INTERACTED_SCREENS.remove(playerId);
        });
    }
}
