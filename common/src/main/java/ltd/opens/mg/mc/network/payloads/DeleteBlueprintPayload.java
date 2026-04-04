package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record DeleteBlueprintPayload(String name) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "delete_blueprint");

    public static DeleteBlueprintPayload decode(FriendlyByteBuf buf) {
        return new DeleteBlueprintPayload(buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, DeleteBlueprintPayload payload) {
        buf.writeUtf(payload.name());
    }
}
