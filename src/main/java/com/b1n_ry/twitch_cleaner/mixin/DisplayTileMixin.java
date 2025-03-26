package com.b1n_ry.twitch_cleaner.mixin;

import me.srrapero720.waterframes.common.block.data.DisplayData;
import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayTile.class)
public abstract class DisplayTileMixin {
    @Shadow @Final public DisplayData data;

    @Shadow public abstract boolean isClient();

    @Inject(method = "load", at = @At("RETURN"))
    private void onLoad(CallbackInfo ci) {
        if (!this.isClient() && this.data.uri.toString().contains("twitch.tv")) {
            this.data.uri = null;
        }
    }
}
