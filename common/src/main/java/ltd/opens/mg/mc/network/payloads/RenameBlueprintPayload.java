package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RenameBlueprintPayload(String oldName, String newName) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "rename_blueprint");

    public static RenameBlueprintPayload decode(FriendlyByteBuf buf) {
        return new RenameBlueprintPayload(buf.readUtf(), buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, RenameBlueprintPayload payload) {
        buf.writeUtf(payload.oldName());
        buf.writeUtf(payload.newName());
    }
}
