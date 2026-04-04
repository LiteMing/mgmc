package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record DuplicateBlueprintPayload(String sourceName, String targetName) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "duplicate_blueprint");

    public static DuplicateBlueprintPayload decode(FriendlyByteBuf buf) {
        return new DuplicateBlueprintPayload(buf.readUtf(), buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, DuplicateBlueprintPayload payload) {
        buf.writeUtf(payload.sourceName());
        buf.writeUtf(payload.targetName());
    }
}
