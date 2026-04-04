package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SaveBlueprintPayload(String name, String data, long expectedVersion) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "save_blueprint");

    public static SaveBlueprintPayload decode(FriendlyByteBuf buf) {
        return new SaveBlueprintPayload(buf.readUtf(), buf.readUtf(1048576), buf.readVarLong());
    }

    public static void encode(FriendlyByteBuf buf, SaveBlueprintPayload payload) {
        buf.writeUtf(payload.name());
        buf.writeUtf(payload.data(), 1048576);
        buf.writeVarLong(payload.expectedVersion());
    }
}
